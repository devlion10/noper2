package kr.go.seoul.noper2.repository;

import kr.go.seoul.noper2.domain.*;
import kr.go.seoul.noper2.dto.FileDTO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Mapper
public interface FileRepository {
    void qaFileUpload(QaBbsFile dto);
    void noticeFileUpload(NoticeBbsFile dto);
    void ledgerLayoutFileUpload(NoperImgFileinfo dto);
    void ledgerGreenBeltLayoutFileUpload(NoperImgFileinfo dto);
    void siteChkFileUpload(NoperSiteChkFile dto);
    void violationBuildingFileUpload(ViomaFileinfo dto);
    void ownerChangeFileUpload(NoperOwnerChangeFile dto);

    Optional<QaBbsFile> findByQAFile(String seq);
    Optional<NoticeBbsFile> findByNoticeFile(String seq);
    List<QaBbsFile> findByQAFileList(ConcurrentHashMap<String, Object> map);
    List<FileDTO> findLedgerLayoutFileList(ConcurrentHashMap<String, Object> map);
    List<FileDTO> findLedgerGreenBeltLayoutFile(ConcurrentHashMap<String, Object> map);
    Optional<FileDTO> findLedgerLayoutFile(FileDTO.RequestDownloadDTO FileDTO);
    Optional<FileDTO> findLedgerGreenBeltLayoutFileDownload(FileDTO.RequestDownloadDTO FileDTO);
    List<FileDTO> noperSiteChkFile(ConcurrentHashMap<String, Object> map);
    List<FileDTO> noperSiteChkFileList(ConcurrentHashMap<String, Object> map);
    List<NoticeBbsFile> findByNoticeFileList(String seq);
    List<FileDTO> violationBuildingFileList(FileDTO fileInfo);
    List<FileDTO> ownerChangeFileList(ConcurrentHashMap<String, Object> map);

    void deleteQAFile(String seq);
    void deleteNoticeFile(String seq);
    void deleteLedgerLayoutFile(FileDTO.RequestDeleteDTO FileDTO);

    void deleteLedgerGreenBeltLayoutFile(FileDTO.RequestDeleteDTO FileDTO);

    void deleteViolationBuildingFile(FileDTO.RequestDeleteDTO FileDTO);
    void deleteOwnerChangeFile(FileDTO.RequestDeleteDTO FileDTO);
    void deleteSiteChkFile(FileDTO.RequestDeleteDTO FileDTO);

    List<Map<String, Object>> getImgFileNmList(List <String> params);

}
