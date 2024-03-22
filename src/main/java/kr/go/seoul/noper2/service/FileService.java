package kr.go.seoul.noper2.service;

import kr.go.seoul.noper2.domain.*;
import kr.go.seoul.noper2.dto.FileDTO;
import kr.go.seoul.noper2.global.auth.UserAuthentication;
import kr.go.seoul.noper2.repository.FileRepository;
import kr.go.seoul.noper2.repository.UnlicensedBuildingManagementRepository;
import kr.go.seoul.noper2.util.Enum.Type;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 업로드, 삭제, 다운로드, 압축 다운로드 등 파일 관련 작업을 수행.
 * 파일과 관련된 모든 로직은 이 서비스에 집중되어 있음.
 *
 * @author sunik
 * Note: 필요에 따라 추가 정보를 기입.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {
    private final FileRepository repository;
    private final UnlicensedBuildingManagementRepository unlicensedBuildingManagementRepository;
    @Value("${noper.media.file.location}")
    private String filePath;

    private final ConcurrentMap<String, Object> map = new ConcurrentHashMap<>();

    /*
     * 서버에 저장시키는 파일은 랜덤 UUID로 들어갑니다.
     * DB에는 원본 파일명과 변경된 파일명 모두 기입됩니다.
     * @param type - 업무파트 구분
     * @param dto - 파일 정보
     * @param files - 파일 데이터
     * @param principal - 로그인 유저의 아이디를 담고있는 객체
     * @throws IOException, SQLException
     */
    
    public void separateFiles(String type, FileDTO dto, MultipartFile[] files, UserAuthentication auth, String flag) throws IOException, SQLException {
        String osName = System.getProperty("os.name");
        log.info("File OS Name: {}", osName);
        String saveFolder;
        if(osName.contains("Windows") || osName.contains("windows") || osName.contains("Mac")){
            saveFolder = Paths.get(filePath, Type.getFileLocation(Type.getType(dto.getBusinessName()))+"/").toString();
        } else { // 개발서버 리눅스, 운영 리눅스 에서
            saveFolder = filePath+"/"+Type.getFileLocation(Type.getType(dto.getBusinessName()))+"/";
        }

        log.debug("saveFolder_loggin: {}", saveFolder);

        File folder = new File(saveFolder);

        if (!folder.exists()){
            boolean executable = folder.setExecutable(true);
            boolean readable = folder.setReadable(true);
            boolean writable = folder.setWritable(true);
            if (!executable || !readable || writable) {
                log.info("File Authority Fail");
            }
            boolean mkdirs = folder.mkdirs();
            if (mkdirs) {
                log.info("Directory created success");
            } else {
                log.info("Failed to create directory");
            }
        }

        for (MultipartFile file : files) {
            FileDTO fileDTO = new FileDTO();
            String originalFileName = file.getOriginalFilename();
            assert originalFileName != null;
            if (!originalFileName.isEmpty()) {
                String saveFileName = UUID.randomUUID() + "." + FileNameUtils.getExtension(originalFileName);
                fileDTO.setSeq(dto.getSeq());
                fileDTO.setBusinessName(dto.getBusinessName());
                fileDTO.setSaveFolder(saveFolder);
                fileDTO.setFileFlag(flag);
                fileDTO.setOriginFile(originalFileName);
                fileDTO.setSaveFile(saveFileName);
                file.transferTo(Paths.get(folder.getPath(), saveFileName));
            }
            log.debug("\n파일 업로드 Seq: {}\n파일 Type: {}", fileDTO.getSeq(), type);
            fileSaveCategory(type, fileDTO, file, auth.getUserId(), flag);
        }
        log.debug(dto.toString());
    }

    /*
     * 각자 테이블 형태가 다르므로, switch문으로 구분합니다.
     * @param type - 업무파트 구분
     * @param fileDTO - 파일 정보
     * @param file - 파일 데이터
     * @param id - 사용자 아이디
     */
    private void fileSaveCategory(String type, FileDTO fileDTO, MultipartFile file, String id, String flag) throws IOException, SQLException {
        Type typeEnum = Type.getType(type);
        switch (typeEnum) {
            case QNA:
                saveQAFile(fileDTO, file, id, flag);
                break;
            case NOTICE:
                saveNoticeFile(fileDTO, file, id);
                break;
            case LEDGER_LAYOUT:
                saveLedgerLayoutFile(fileDTO, file, id, flag);
                break;
            case LEDGER_GREEN_BELT_LAYOUT:
                saveLedgerGreenBeltLayoutFile(fileDTO, file, id, flag);
                break;
            case LEDGER_SITE_CHK:
                saveLedgerSiteChkFile(fileDTO, file, id, flag);
                break;
            case VIOLATION_BUILDING:
                saveViolationBuilding(fileDTO, file, id, flag);
                break;
            case OWNER_CHANGE:
                saveOwnerChangeFile(fileDTO, file, id, flag);
                break;
        }
    }

    private void saveViolationBuilding(FileDTO fileDTO, MultipartFile files, String id, String flag) {
        var file = ViomaFileinfo.builder()
                .gmskk(fileDTO.getSeq())
                .logicalFilename(fileDTO.getOriginFile())
                .physicalFilename(fileDTO.getSaveFile())
                .fileFlag(fileDTO.getFileFlag())
                .filePath(fileDTO.getSaveFolder())
                .fileEnd(FilenameUtils.getExtension(files.getOriginalFilename()))
                .fileSize(BigDecimal.valueOf(files.getSize()))
                .build();
        repository.violationBuildingFileUpload(file);
    }

    /*
     * 도메인 클래스에 담아서 Repository로 넘김.
     * @param fileDTO - 파일 정보
     * @param files - 파일 데이터
     * @param id - 사용자 아이디
     */
    private void saveQAFile(FileDTO fileDTO, MultipartFile files, String id, String flag) {
        var file = QaBbsFile.builder()
                .qaSeq(Long.valueOf(fileDTO.getSeq()))
                .logicalFilename(fileDTO.getOriginFile())
                .physicalFilename(fileDTO.getSaveFile())
                .filePath(fileDTO.getSaveFolder())
                .fileEnd(FilenameUtils.getExtension(files.getOriginalFilename()))
                .fileSize(BigDecimal.valueOf(files.getSize()))
                .qaFlag(flag)
                .registId(id)
                .updateId(id)
                .build();
        repository.qaFileUpload(file);
    }

    /*
     * 도메인 클래스에 담아서 Repository로 넘김.
     * @param fileDTO - 파일 정보
     * @param files - 파일 데이터
     * @param id - 사용자 아이디
     */
    private void saveNoticeFile(FileDTO fileDTO, MultipartFile files, String id) {
        var file = NoticeBbsFile.builder()
                .noticeSeq(String.valueOf(fileDTO.getSeq()))
                .logicalFilename(fileDTO.getOriginFile())
                .physicalFilename(fileDTO.getSaveFile())
                .filePath(fileDTO.getSaveFolder())
                .fileEnd(FilenameUtils.getExtension(files.getOriginalFilename()))
                .fileSize(BigDecimal.valueOf(files.getSize()))
                .registId(id)
                .updateId(id)
                .build();
        repository.noticeFileUpload(file);
    }

    private void saveLedgerLayoutFile(FileDTO fileDTO, MultipartFile files, String id, String flag) {
        String[] gmbunhoArr = fileDTO.getSeq().split("-");
        String gmskk = gmbunhoArr[0];
        String gmdjgb = gmbunhoArr[1];
        String gmseqco = gmbunhoArr[2];
        var file = NoperImgFileinfo.builder()
                .logicalFilename(fileDTO.getOriginFile())
                .gmskk(gmskk)
                .gmdjgb(gmdjgb)
                .gmseqco(gmseqco)
                .fileFlag(flag)
                .physicalFilename(fileDTO.getSaveFile())
                .filePath(fileDTO.getSaveFolder())
                .fileEnd(FilenameUtils.getExtension(files.getOriginalFilename()))
                .fileSize(BigDecimal.valueOf(files.getSize()))
                .registId(id)
                .updateId(id)
                .build();
        repository.ledgerLayoutFileUpload(file);
    }

    private void saveLedgerGreenBeltLayoutFile(FileDTO fileDTO, MultipartFile files, String id, String flag) {
        String admSeq = fileDTO.getSeq();
        var file = NoperImgFileinfo.builder()
                .logicalFilename(fileDTO.getOriginFile())
                .admSeq(admSeq)
                .fileFlag(flag)
                .physicalFilename(fileDTO.getSaveFile())
                .filePath(fileDTO.getSaveFolder())
                .fileEnd(FilenameUtils.getExtension(files.getOriginalFilename()))
                .fileSize(BigDecimal.valueOf(files.getSize()))
                .registId(id)
                .updateId(id)
                .build();
        repository.ledgerGreenBeltLayoutFileUpload(file);
    }

    private void saveLedgerSiteChkFile(FileDTO fileDTO, MultipartFile files, String id, String flag) {
        String[] gmbunhoArr = fileDTO.getSeq().split("-");
        String gmskk = gmbunhoArr[0];
        String gmdjgb = gmbunhoArr[1];
        String gmseqco = gmbunhoArr[2];
        String noperChkino = "";

        if(!flag.trim().equals("")){
            noperChkino = flag;
        }else{
            String chkiNo = unlicensedBuildingManagementRepository.nowNoperChkino(gmskk, gmdjgb, gmseqco);
            if(chkiNo.equals("0")){
                noperChkino = chkiNo + 1;
            }else {
                noperChkino = chkiNo;
            }
        }
        var file = NoperSiteChkFile.builder()
                .gmskk(gmskk)
                .gmdjgb(gmdjgb)
                .gmseqco(gmseqco)
                .chkilno(noperChkino)
                .fileFlag("1")
                .logicalFilename(fileDTO.getOriginFile())
                .physicalFilename(fileDTO.getSaveFile())
                .filePath(fileDTO.getSaveFolder())
                .fileEnd(FilenameUtils.getExtension(files.getOriginalFilename()))
                .fileSize(BigDecimal.valueOf(files.getSize()))
                .regId(id)
                .build();
        repository.siteChkFileUpload(file);
    }

    private void saveOwnerChangeFile(FileDTO fileDTO, MultipartFile files, String id, String flag) {
        String[] gmbunhoArr = fileDTO.getSeq().split("-");
        String[] flagArr = flag.split("-");

        String suilno = unlicensedBuildingManagementRepository.nowNoperSuilNo(gmbunhoArr[0], gmbunhoArr[1], gmbunhoArr[2]);
        var file = NoperOwnerChangeFile.builder()
                .gmjilno(flagArr[0])
                .gmskk(gmbunhoArr[0])
                .gmdjgb(gmbunhoArr[1])
                .fileSub(flagArr[1])
                .gmseqco(gmbunhoArr[2])
                .suilno(suilno)
                .logicalFilename(fileDTO.getOriginFile())
                .physicalFilename(fileDTO.getSaveFile())
                .filePath(fileDTO.getSaveFolder())
                .fileEnd(FilenameUtils.getExtension(files.getOriginalFilename()))
                .fileSize(BigDecimal.valueOf(files.getSize()))
                .registId(id)
                .build();
        repository.ownerChangeFileUpload(file);
    }

    public List<FileDTO.ResponseFileDTO> readFile(String type, String seq, String flag) throws IOException, SQLException {
        Type typeEnum = Type.getType(type);
        switch (typeEnum) {
            case QNA:
                return readQAFile(seq, flag);
            case NOTICE:
                return readNoticeFile(seq);
            case LEDGER_LAYOUT:
                return ledgerLayoutFile(type, seq, flag);
            case LEDGER_GREEN_BELT_LAYOUT:
                return ledgerGreenBeltLayoutFile(type, seq, flag);
            case LEDGER_SITE_CHK:
                return noperSiteChkFileList(type, seq, flag);
            case VIOLATION_BUILDING:
                return violationBuildingFileList(type, seq, flag);
            case OWNER_CHANGE:
                return ownerChangeFileList(type, seq, flag);
            default:
                throw new IOException("Type is Not Found.");
        }
    }

    private List<FileDTO.ResponseFileDTO> violationBuildingFileList(String type, String seq, String flag) {
        FileDTO fileInfo = new FileDTO();
        fileInfo.setGmskk(seq);
        fileInfo.setFileFlag(flag);
        return repository.violationBuildingFileList(fileInfo)
                .stream().map(f -> FileDTO.ResponseFileDTO.builder()
                        .fSeq(f.getFileSeq())
                        .seq(String.valueOf(f.getSeq()))
                        .isClient("N")
                        .logicalFilename(f.getLogicalFilename())
                        .physicalFilename(f.getPhysicalFilename())
                        .fileSize(f.getFileSize())
                        .fileEnd(f.getFileEnd())
                        .fileFlag(f.getFileFlag())
                        .regDtime(f.getRegDtime())
                        .gmskk(f.getGmskk())
                        .filePath(f.getFilePath())
                        .physicalFilename(f.getPhysicalFilename())
                        .build()
                ).collect(Collectors.toList());
    }

    private List<FileDTO.ResponseFileDTO> readQAFile(String seq, String flag) throws IOException, SQLException {
        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
        map.put("seq", seq);
        map.put("flag", flag);
        return repository.findByQAFileList(map)
                .stream().map(f -> FileDTO.ResponseFileDTO.builder()
                        .fSeq(f.getFileSeq())
                        .seq(f.getQaSeq().toString())
                        .isClient("N")
                        .logicalFilename(f.getLogicalFilename())
                        .physicalFilename(f.getPhysicalFilename())
                        .fileSize(f.getFileSize())
                        .fileEnd(f.getFileEnd())
                        .filePath(f.getFilePath())
                        .flag(f.getQaFlag())
                        .build()
                ).collect(Collectors.toList());
    }

    private List<FileDTO.ResponseFileDTO> readNoticeFile(String seq) throws IOException, SQLException {
        return repository.findByNoticeFileList(seq)
                .stream().map(f -> FileDTO.ResponseFileDTO.builder()
                        .fSeq(f.getFileSeq())
                        .seq(String.valueOf(f.getNoticeSeq()))
                        .isClient("N")
                        .logicalFilename(f.getLogicalFilename())
                        .physicalFilename(f.getPhysicalFilename())
                        .fileSize(f.getFileSize())
                        .fileEnd(f.getFileEnd())
                        .filePath(f.getFilePath())
                        .build()
                ).collect(Collectors.toList());
    }

    private List<FileDTO.ResponseFileDTO> ledgerLayoutFile(String type, String seq, String flag) throws IOException, SQLException {
        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
        String[] gmbunhoArr = seq.split("-");
        map.put("gmskk", gmbunhoArr[0]);
        map.put("gmdjgb", gmbunhoArr[1]);
        map.put("gmseqco", gmbunhoArr[2]);
        map.put("type", type);
        map.put("flag", flag);
        return repository.findLedgerLayoutFileList(map)
                    .stream().map(f -> FileDTO.ResponseFileDTO.builder()
                            .fSeq(f.getFileSeq())
                            .seq(f.getSeq())
                            .isClient("N")
                            .logicalFilename(f.getLogicalFilename())
                            .fileSize(f.getFileSize())
                            .fileEnd(f.getFileEnd())
                            .flag(f.getFileFlag())
                            .registTs(f.getRegistTs())
                            .gmskk(f.getGmskk())
                            .gmdjgb(f.getGmdjgb())
                            .gmseqco(f.getGmseqco())
                            .filePath(f.getFilePath())
                            .physicalFilename(f.getPhysicalFilename())
                            .build()
                    ).collect(Collectors.toList());
    }

    private List<FileDTO.ResponseFileDTO> ledgerGreenBeltLayoutFile(String type, String seq, String flag) {
        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
        map.put("type", type);
        map.put("seq", seq);
        map.put("flag", flag);
        return repository.findLedgerGreenBeltLayoutFile(map)
                .stream().map(f -> FileDTO.ResponseFileDTO.builder()
                        .fSeq(f.getFileSeq())
                        .seq(String.valueOf(f.getSeq()))
                        .isClient("N")
                        .logicalFilename(f.getLogicalFilename())
                        .fileSize(f.getFileSize())
                        .fileEnd(f.getFileEnd())
                        .flag(f.getFileFlag())
                        .registTs(f.getRegistTs())
                        .filePath(f.getFilePath())
                        .physicalFilename(f.getPhysicalFilename())
                        .build()
                ).collect(Collectors.toList());
    }
    private List<FileDTO.ResponseFileDTO> noperSiteChkFileList(String type, String seq, String chkilno) throws IOException, SQLException {
        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
        String[] gmbunhoArr = seq.split("-");
        map.put("gmskk", gmbunhoArr[0]);
        switch(gmbunhoArr[1]) {
            case "등재" : map.put("gmdjgb", "0"); break;
            case "미등재" : map.put("gmdjgb", "1"); break;
            case "항측판독" : map.put("gmdjgb", "2"); break;
            default: map.put("gmdjgb", gmbunhoArr[1]); break;
        }
        map.put("gmseqco", gmbunhoArr[2]);
        map.put("type", type);
        map.put("chkilno", chkilno);
        return repository.noperSiteChkFileList(map)
                .stream().map(f -> FileDTO.ResponseFileDTO.builder()
                        .fSeq(f.getFileSeq())
                        .seq(String.valueOf(f.getSeq()))
                        .isClient("N")
                        .logicalFilename(f.getLogicalFilename())
                        .fileSize(f.getFileSize())
                        .fileEnd(f.getFileEnd())
                        .flag(f.getFileFlag())
                        .regDtime(f.getRegDtime())
                        .gmskk(f.getGmskk())
                        .regId(f.getRegId())
                        .chkilno(f.getChkilno())
                        .gmdjgb(f.getGmdjgb())
                        .gmseqco(f.getGmseqco())
                        .filePath(f.getFilePath())
                        .physicalFilename(f.getPhysicalFilename())
                        .build()
                ).collect(Collectors.toList());
    }

    private List<FileDTO.ResponseFileDTO> ownerChangeFileList(String type, String seq, String suilno) throws IOException, SQLException {
        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
        String[] gmbunhoArr = seq.split("-");
        map.put("gmskk", gmbunhoArr[0]);
        map.put("gmdjgb", gmbunhoArr[1]);
        map.put("gmseqco", gmbunhoArr[2]);
        map.put("suilno", gmbunhoArr[3]);
        return repository.ownerChangeFileList(map)
                .stream().map(f -> FileDTO.ResponseFileDTO.builder()
                        .fSeq(f.getFileSeq())
                        .seq(String.valueOf(f.getSeq()))
                        .logicalFilename(f.getLogicalFilename())
                        .fileSize(f.getFileSize())
                        .fileEnd(f.getFileEnd())
                        .flag(f.getFileFlag())
                        .regDtime(f.getRegDtime())
                        .gmskk(f.getGmskk())
                        .gmdjgb(f.getGmdjgb())
                        .gmseqco(f.getGmseqco())
                        .suilno(f.getSuilno())
                        .fileSub(f.getFileSub())
                        .filePath(f.getFilePath())
                        .physicalFilename(f.getPhysicalFilename())
                        .build()
                ).collect(Collectors.toList());
    }

    public void deleteFile(List<FileDTO.RequestDeleteDTO> dto, String type) throws IOException, SQLException {
        Type typeEnum = Type.getType(type);
        switch (typeEnum) {
            case QNA:
                this.deleteQAFile(dto);
                break;
            case NOTICE:
                this.deleteNoticeFile(dto);
                break;
            case LEDGER_LAYOUT:
                this.deleteLedgerLayoutFileFile(dto);
                break;
            case LEDGER_GREEN_BELT_LAYOUT:
                this.deleteLedgerGreenBeltLayoutFile(dto);
                break;
            case VIOLATION_BUILDING:
                this.deleteViolationBuildingFile(dto);
                break;
            case OWNER_CHANGE:
                this.deleteOwnerChangeFile(dto);
                break;
            case LEDGER_SITE_CHK:
                this.delSiteChk(dto);
                break;
            default:
                throw new IOException("옳지않은 URL PATH 입니다.");
        }
    }

    
    public void deleteOwnerChangeFile(List<FileDTO.RequestDeleteDTO> dto) throws IOException, SQLException {
        for (FileDTO.RequestDeleteDTO data : dto) {
            String saveFolder = data.getFilePath();
            String decodeFileName = URLDecoder.decode(data.getPhysicalFilename(), StandardCharsets.UTF_8);
            File file = new File(saveFolder + File.separator + decodeFileName);
            repository.deleteOwnerChangeFile(data);
            file.delete();
        }
    }
    public void delSiteChk(List<FileDTO.RequestDeleteDTO> dto) throws IOException, SQLException {
        for (FileDTO.RequestDeleteDTO data : dto) {
            String saveFolder = data.getFilePath();
            String decodeFileName = URLDecoder.decode(data.getPhysicalFilename(), StandardCharsets.UTF_8);
            File file = new File(saveFolder + File.separator + decodeFileName);
            repository.deleteSiteChkFile(data);
            file.delete();
        }
    }



    
    public void deleteViolationBuildingFile(List<FileDTO.RequestDeleteDTO> dto) throws IOException, SQLException {
        for (FileDTO.RequestDeleteDTO data : dto) {
            String saveFolder = data.getFilePath();
            String decodeFileName = URLDecoder.decode(data.getPhysicalFilename(), StandardCharsets.UTF_8);
            File file = new File(saveFolder + File.separator + decodeFileName);
            repository.deleteViolationBuildingFile(data);
            file.delete();
        }

    }

    
    public void deleteQAFile(List<FileDTO.RequestDeleteDTO> dto) throws IOException, SQLException {
        for (FileDTO.RequestDeleteDTO data : dto) {
            QaBbsFile dbFile = repository.findByQAFile(data.getFseq()).orElseThrow(SQLException::new);
            String saveFolder = dbFile.getFilePath();
            String decodeFileName = URLDecoder.decode(dbFile.getPhysicalFilename(), StandardCharsets.UTF_8);
            File file = new File(saveFolder + File.separator + decodeFileName);
            repository.deleteQAFile(data.getFseq());
            file.delete();
        }
    }

    
    public void deleteNoticeFile(List<FileDTO.RequestDeleteDTO> dto) throws IOException, SQLException {
        for (FileDTO.RequestDeleteDTO data : dto) {
            NoticeBbsFile dbFile = repository.findByNoticeFile(data.getFseq()).orElseThrow(SQLException::new);
            String saveFolder = dbFile.getFilePath();
            String decodeFileName = URLDecoder.decode(dbFile.getPhysicalFilename(), StandardCharsets.UTF_8);
            File file = new File(saveFolder + File.separator + decodeFileName);
            repository.deleteNoticeFile(data.getFseq());
            file.delete();
        }
    }

    
    public void deleteLedgerLayoutFileFile(List<FileDTO.RequestDeleteDTO> dto) throws IOException, SQLException {
        for (FileDTO.RequestDeleteDTO data : dto) {
            String saveFolder = data.getFilePath();
            String decodeFileName = URLDecoder.decode(data.getPhysicalFilename(), StandardCharsets.UTF_8);
            File file = new File(saveFolder + File.separator + decodeFileName);
            repository.deleteLedgerLayoutFile(data);
            file.delete();
        }
    }

    
    public void deleteLedgerGreenBeltLayoutFile(List<FileDTO.RequestDeleteDTO> dto) throws IOException, SQLException {
        for (FileDTO.RequestDeleteDTO data : dto) {
            String saveFolder = data.getFilePath();
            String decodeFileName = URLDecoder.decode(data.getPhysicalFilename(), StandardCharsets.UTF_8);
            File file = new File(saveFolder + File.separator + decodeFileName);
            repository.deleteLedgerGreenBeltLayoutFile(data);
            file.delete();
        }
    }

    /*
     *
     * @param dto
     * @param response
     * @throws IOException, SQLException
     */
    public void zipFileDownload(FileDTO.RequestDownloadDTO dto, HttpServletResponse response) throws IOException, SQLException {
        ZipOutputStream zos = null;
        FileInputStream in = null;
        try {
            String path = "";
            zos = new ZipOutputStream(response.getOutputStream());

            path = Paths.get(filePath, Type.getFileLocation(Type.getType(dto.getType()))).toString();

            Set<String> addedFilenames = new HashSet<>();

            for (FileDTO.GridDTO data : dto.getGrid()) {
                getFileName(dto, data);
                String logicalFileName = data.getLogicalFilename();
                String physicalFileName = data.getPhysicalFilename();
                File file = new File(path, physicalFileName);

                String zipEntryFilename = logicalFileName;
                int fileCounter = 1;
                while (addedFilenames.contains(zipEntryFilename)) {
                    String baseName = FilenameUtils.getBaseName(logicalFileName);
                    String extension = FilenameUtils.getExtension(logicalFileName);
                    zipEntryFilename = baseName + " (" + fileCounter + ")" + "." + extension;
                    fileCounter++;
                }

                in = new FileInputStream(file);
                zos.putNextEntry(new ZipEntry(zipEntryFilename));
                StreamUtils.copy(in, zos);
                in.close();
                zos.closeEntry();

                // Add the filename to the set of added filenames
                addedFilenames.add(zipEntryFilename);
            }
        } catch (IOException e) {
            log.error("zipFileDownload: ", e);
        } finally {
            in.close();
            zos.close();
        }
        response.getOutputStream().flush();
    }

    /*
     *
     * @param dto - request dto
     * @param data - grid Data
     * @throws IOException, SQLException - cause: type is a null
     */
    private void getFileName(FileDTO.RequestDownloadDTO dto, FileDTO.GridDTO data) throws IOException, SQLException, SQLException {
        Type typeEnum = Type.getType(dto.getType());
        switch (typeEnum) {
            case QNA:
            case VIOLATION_BUILDING:
            case NOTICE: break;
            case LEDGER_LAYOUT: {
                String[] gmbunhoArr = dto.getSeq().split("-");
                String gmskk = gmbunhoArr[0];
                String gmdjgb = "";
                switch (gmbunhoArr[1]) {
                    case "등재": gmdjgb = "0"; break;
                    case "미등재": gmdjgb = "1"; break;
                    case "항측판독": gmdjgb = "2"; break;
                    default: gmdjgb = gmbunhoArr[1]; break;
                }
                String gmseqco = gmbunhoArr[2];
                String flag = "";
                dto.setGmskk(gmskk);
                dto.setGmdjgb(gmdjgb);
                dto.setGmseqco(gmseqco);
                switch(dto.getGrid().get(0).getFileFlag()) {
                    case "배치도" : flag = "1"; break;
                    case "사진" : flag = "2"; break;
                    default: flag = "0"; break;
                }
                dto.setFseq(dto.getGrid().get(0).getFseq());
                dto.setFlag(flag);
                FileDTO file = repository.findLedgerLayoutFile(dto).orElseThrow(SQLException::new);
                map.put("logical", file.getLogicalFilename());
                map.put("physical", file.getPhysicalFilename());
                break;
            }
            case LEDGER_GREEN_BELT_LAYOUT: {
                String admSeq = dto.getSeq();
                dto.setSeq(admSeq);
                dto.setFseq(dto.getGrid().get(0).getFseq());
                FileDTO file = repository.findLedgerGreenBeltLayoutFileDownload(dto).orElseThrow(SQLException::new);
                map.put("logical", file.getLogicalFilename());
                map.put("physical", file.getPhysicalFilename());
                break;
            }
            default:
                throw new IOException("부적절한 식별자입니다.");
        }
    }

    
    public void deleteAllFile(Long noticeSeq, String type) throws IOException, SQLException {
        List<FileDTO.ResponseFileDTO> fileData = readFile(type, noticeSeq.toString(), "1L");
        List<FileDTO.RequestDeleteDTO> dtoList = fileData.stream()
                .map(f -> FileDTO.RequestDeleteDTO.builder()
                            .filePath(f.getFilePath())
                            .fseq(f.getFSeq().toString())
                            .build())
                .collect(Collectors.toList());
        deleteFile(dtoList, type);
    }

    public List<Map<String, Object>> getImgFileNmList(List <String> params) throws IOException, SQLException {
        List<Map<String, Object>> list = repository.getImgFileNmList(params);
        return list;
    }
}