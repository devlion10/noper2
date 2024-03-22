package kr.go.seoul.noper2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.go.seoul.noper2.dto.FileDTO;
import kr.go.seoul.noper2.global.auth.UserAuthentication;
import kr.go.seoul.noper2.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.stream.StreamSource;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 업로드, 삭제, 다운로드, 압축 다운로드 등 파일 관련 작업을 수행.
 * 파일과 관련된 모든 작업은 이 컨트롤러에 집중되어 있음.
 *
 * @author sunik
 * Note: 필요에 따라 추가 정보를 기입.
 */
@Slf4j
@RequestMapping("/file")
@RequiredArgsConstructor
@RestController
public class FileController {
    private final FileService service;

    @Value("${spring.profiles.active}")
    private String profile;

    @Value("${noper.media.file.location}")
    private String filePath;

    @Value("${noper.airial.file.window_location}")
    private String window_airialFilePath;

    @Value("${noper.airial.file.linux_location}")
    private String linux_airialFilePath;

     @Autowired
     ResourceLoader resourceLoader;

    /*
     * readFileQA() 에서 flag Path가 빠졌습니다.
     * 해당 Notice 테이블에서는 Flag가 없기에 제외됩니다.
     * @param type - 업무파트 구분
     * @param seq - 화면 seq
     * @return FileList 반환
     * @throws Exception
     */
    @GetMapping("/{type}/{seq}")
    public ResponseEntity<List<FileDTO.ResponseFileDTO>> readFileBySequence(@PathVariable String type,
                                                                            @PathVariable String seq) throws IOException, Exception {
        return ResponseEntity.ok(service.readFile(type, seq, "1L"));
    }
    /*
     * Ajax 요청시 데이터를 넣기보다 쉽게하기위해 PathVariable을 사용했습니다.
     * @param type - 업무파트 구분
     * @param seq - 화면 seq
     * @param flag - flag가 테이블에 존재할 경우 사용.
     * @return FileList 반환
     * @throws Exception
     */
    @GetMapping("/{type}/{seq}/{flag}")
    public ResponseEntity<List<FileDTO.ResponseFileDTO>> readFileBySequenceAndFlag(@PathVariable String type,
                                                                    @PathVariable String seq,
                                                                    @PathVariable String flag) throws IOException, Exception {
        return ResponseEntity.ok(service.readFile(type, seq, flag));
    }
    /*
     * 파일 업로드 버튼 입력 후, 데이터를 받는 컨트롤러
     * @param type - 업무파트 구분
     * @param files - 파일 데이터
     * @param dto - 파일 정보
     * @param principal - 로그인 유저의 아이디를 담고있는 객체
     * @return HTTP 201 반환
     * @throws Exception
     */
    @PostMapping("/{type}/write")
    public ResponseEntity<Void> write(@PathVariable String type,
                                      @RequestPart("file") MultipartFile[] files,
                                      FileDTO dto,
                                      @AuthenticationPrincipal UserAuthentication auth,
                                      String flag ) throws IOException, Exception {
        service.separateFiles(type, dto, files, auth, flag);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    /*
     * 선택한 파일 삭제
     * @param type - 업무파트 구분
     * @param fSeq - 해당 파일의 시퀀스
     * @return Void 요청이기에, content를 반환하지 않음.
     * @throws Exception
     */
    @DeleteMapping("/{type}")
    public ResponseEntity<Void> deleteFile(@PathVariable String type,
                                           @RequestBody List<FileDTO.RequestDeleteDTO> dto) throws IOException, Exception {
        service.deleteFile(dto, type);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/download-file")
    public ResponseEntity<Void> downloadFile(@RequestBody FileDTO.RequestDownloadDTO dto,
                                             HttpServletResponse response) throws IOException, Exception {

        String path = dto.getFilePath();
        String fileNm = dto.getPhysicalFilename();
        log.debug("FILE_PATH DEBUG: {}", path);
        log.debug("FILE_NAME DEBUG: {}", fileNm);
        log.info("FILE_PATH INFO: {}", path);
        log.info("FILE_NAME INFO: {}", fileNm);
        byte[] files = FileUtils.readFileToByteArray(new File(Paths.get(path, dto.getPhysicalFilename()).toString()));
        response.setContentType("application/octet-stream");
        response.setContentLength(files.length);
        response.setHeader("Content-Disposition","attachment; fileName=\""+ URLEncoder.encode(dto.getPhysicalFilename(), StandardCharsets.UTF_8)+"\";");
        response.setHeader("Content-Transfer-Encoding","binary");

        response.getOutputStream().write(files);
        response.getOutputStream().flush();
        response.getOutputStream().close();
        return ResponseEntity.noContent().build();
    }

    // 이외의 다른 파일을 다운로드하기위해 사용함
    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        Path path = Paths.get(filePath).resolve(fileName);

        try {
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() && resource.isReadable()) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", fileName);

                return ResponseEntity.ok()
                        .headers(headers)
                        .body(resource);
            } else {
                // 파일이 존재하지 않을 경우에 대한 예외 처리
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            log.error("URL 형식이 잘못되었거나 파일 취소", e);
            // URL 형식이 잘못되었을 경우에 대한 예외 처리
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/download-file/all")
    public ResponseEntity<Void> downloadAllFile(@RequestBody FileDTO.RequestDownloadDTO dto,
                                                HttpServletResponse response) throws IOException, Exception {
        service.zipFileDownload(dto, response);

        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=\""+ URLEncoder.encode("download", StandardCharsets.UTF_8) + ".zip\"");
        response.setHeader("Content-Transfer-Encoding","binary");

        response.getOutputStream().flush();
        response.getOutputStream().close();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/getAirialImgFilesNmList")
    @ResponseBody
    public HashMap<String, Object> getAirialImgFilesNmList(@RequestBody String data,
            HttpServletResponse response) throws IOException, Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        log.debug("getAirialImgFilesNmList___");
        JSONParser parser = new JSONParser();
        JSONObject jsonobj = null;
        try {
            jsonobj = (JSONObject) parser.parse(data);
            log.debug("getAirialImgFilesNmList_____parameters");
            log.debug(String.valueOf(jsonobj));
        } catch (ParseException e) {
            log.error("airial Json Parse Error: ", e);
        }

        Map<String, Object> paramMap=new HashMap<>();
        paramMap = new ObjectMapper().readValue(jsonobj.toString(), Map.class);
        List <String> paramList = new ArrayList<>();

        paramList=(ArrayList)paramMap.get("paramList");
        List<Map<String, Object>> qrResList=new ArrayList<>();
        try {
            qrResList= service.getImgFileNmList(paramList);
        } catch (RuntimeException e) {
            log.error("airial Query Error: ", e);
        }
//        StringBuffer fileNm= new StringBuffer();
//        String DirNm= "";
//        for(int i=0;i<qrResList.size();i++){
//            DirNm="D:/서울무허가도면index/도면_이미지/"+qrResList.get(i).get("GU_NM").toString();
//            fileNm= new StringBuffer();
//            fileNm.append(qrResList.get(i).get("IMG_NM").toString());
//            fileNm.append(".");
//            fileNm.append(qrResList.get(i).get("FILE_CDE").toString());
//
//            File airialImgFile = new File(DirNm +"/"+ fileNm);
//            String base64ImgStr = "";
//            if (airialImgFile.exists() && airialImgFile.isFile() && airialImgFile.length() > 0) {
//                log.debug("airialImgFile is exist__");
//                byte[] bt = new byte[(int) airialImgFile.length()];
//                FileInputStream fis = null;
//                try {
//                    fis = new FileInputStream(airialImgFile);
//                    fis.read(bt);
////                    Base64.Encoder encoder = Base64.getEncoder();
////                    byte[] encodedBytes = encoder.encode(bt);
////                    base64Img=encodedBytes.toString();
//                    base64ImgStr = new String(Base64.encodeBase64(bt));
//                    qrResList.get(i).put("base64",base64ImgStr);
//                } catch (Exception e) {
//                    throw e;
//                } finally {
//                    try {
//                        if (fis != null) {
//                            fis.close();
//                        }
//                    } catch (IOException e) {
//                    } catch (Exception e) {
//                    }
//                }
//
//            }
//        }파일읽어오는 for문 end

        resultMap.put("resultList",qrResList);
        return resultMap;
    }

    //@PostMapping("/getAirialImgFile")
    @GetMapping("/getAirialImgFile/{guNm}/{fileNm}")
    //@ResponseBody
    public ResponseEntity<Object> getAirialImgFile(
            HttpServletRequest req,
            @PathVariable String guNm, @PathVariable String fileNm,
            HttpServletResponse response) throws IOException, Exception {

        try {
            req.setCharacterEncoding("UTF-8");
            //String paramStr= URLDecoder.decode(data,StandardCharsets.UTF_8);
            //String guNm= req.getParameter("guNm");
            //String fileNm=req.getParameter("imgFileNm");

            if(profile.equals("prod")) {
                guNm = new String(guNm.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                fileNm = new String(fileNm.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            }

            String[] fileFullNmArr = fileNm.split("\\.");
            String mediaType;
            if(fileFullNmArr[1].equals("jpg")||fileFullNmArr[1].equals("JPG")||fileFullNmArr[1].equals("pdf")||fileFullNmArr[1].equals("PDF")){
                mediaType="image/jpeg" ;
            }else if(fileFullNmArr[1].equals("png")||fileFullNmArr[1].equals("PNG")){
                mediaType="image/png" ;
            }else{
                mediaType="application/pdf";
            }

            String osName = System.getProperty("os.name");
            String filePath;
            if(osName.contains("Windows") || osName.contains("windows") || osName.contains("Mac")){
                log.info("OS Name: {}", osName);
                filePath="file:" + window_airialFilePath+"/"+guNm+"/";
            }else {
                log.info("OS Name: {}", osName);
                filePath="file:" + linux_airialFilePath+"/"+guNm+"/";

            }
            log.info("filePath: {}", filePath);
            log.info("fileNm: {}", fileNm);

            Resource resource = resourceLoader.getResource(filePath + fileNm);
            File file=resource.getFile();

            if(fileFullNmArr[1].equals("pdf")||fileFullNmArr[1].equals("PDF")) {
                PDDocument document = PDDocument.load(file);
                PDFRenderer renderer = new PDFRenderer(document);
                RenderedImage image = renderer.renderImage(0);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(image, "jpeg", os);
                resource = new ByteArrayResource(os.toByteArray());
                document.close();
                os.close();
            }

            log.info("getAbsoluteFile: {}",file.getAbsoluteFile());
            log.info("length: {}",file.length());
            log.info("resourceLength: {}",resource.contentLength());
            log.info("getPath: {}",file.getPath());
            log.info("getName: {}",file.getName());
            log.info("mediaType: {}",mediaType);
            log.info("String.valueOf(file.length: {}",String.valueOf(file.length()));

            String fileNmEnc=URLEncoder.encode(fileNm, "UTF-8");
            log.info("fileNmEnc: {}",fileNmEnc);
            return ResponseEntity.ok()
                    //.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileNm + "\"")
                    //.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + "ss" + "\"")
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength()))
                    //.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM.toString())
                    //.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF.toString())
                    .header(HttpHeaders.CONTENT_TYPE, mediaType)
                    .body(resource);
        } catch (FileNotFoundException e) {
            log.error("airialFile NotFound Error: ", e);
            return ResponseEntity.badRequest().body(null);
        } catch (IOException e) {
            log.error("airialFile Server Error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        //return resultMap;
    }

    @GetMapping("/getAirialImgFile/download/{guNm}/{fileNm}")
    //@ResponseBody
    public ResponseEntity<Object> getAirialImgFileDownload(
            @PathVariable String guNm, @PathVariable String fileNm) throws IOException, Exception {

        try {
            if(profile.equals("prod")) {
                guNm = new String(guNm.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                fileNm = new String(fileNm.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            }

            String[] fileFullNmArr = fileNm.split("\\.");
            String mediaType;
            if(fileFullNmArr[1].equals("pdf")||fileFullNmArr[1].equals("PDF")){
                mediaType="application/pdf";
            }else if(fileFullNmArr[1].equals("jpg")||fileFullNmArr[1].equals("JPG")){
                mediaType="image/jpeg" ;
            }else if(fileFullNmArr[1].equals("png")||fileFullNmArr[1].equals("PNG")){
                mediaType="image/png" ;
            }else{
                mediaType="application/pdf";
            }

            String osName = System.getProperty("os.name");
            String filePath;
            if(osName.contains("Windows") || osName.contains("windows") || osName.contains("Mac")){
                log.info("OS Name: {}", osName);
                filePath="file:" + window_airialFilePath+"/"+guNm+"/";
            }else {
                log.info("OS Name: {}", osName);
                filePath="file:" + linux_airialFilePath+"/"+guNm+"/";

            }
            log.info("filePath: {}", filePath);
            log.info("fileNm: {}", fileNm);

            Resource resource = resourceLoader.getResource(filePath + fileNm);
            File file=resource.getFile();

            log.info("getPath: {}",file.getPath());
            log.info("getName: {}",file.getName());
            log.info("mediaType: {}",mediaType);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileNm + "\"")
                    //.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + "ss" + "\"")
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength()))
                    //.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM.toString())
                    //.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF.toString())
                    .header(HttpHeaders.CONTENT_TYPE, mediaType)
                    .body(resource);
        } catch (FileNotFoundException e) {
            log.error("airialFile NotFound Error: ", e);
            return ResponseEntity.badRequest().body(null);
        } catch (IOException e) {
            log.error("airialFile Server Error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        //return resultMap;
    }
}
