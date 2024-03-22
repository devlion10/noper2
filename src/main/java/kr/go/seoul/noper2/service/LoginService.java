package kr.go.seoul.noper2.service;

import kr.go.seoul.noper2.domain.HisNoperUserLogin;
import kr.go.seoul.noper2.domain.NoperUserDamo;
import kr.go.seoul.noper2.dto.LoginDTO;
import kr.go.seoul.noper2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public void saveLoginHistory(String id, String ip) throws IOException, SQLException, NoSuchElementException {
        HisNoperUserLogin hisNoperUserLogin = HisNoperUserLogin.builder()
                .userId(id)
                .loginIp(ip)
                .build();
        repository.saveHistory(hisNoperUserLogin);
    }
    @Transactional
    public void saveLoginSessionDefault(String minute) {
        repository.updateSessionTime(minute);
    }
    public Integer findSessionTime() throws IOException, SQLException, NoSuchElementException, NoSuchElementException {
        Optional<String> sessionTime = repository.findSession();
        if (sessionTime.isPresent()) {
            return Integer.parseInt(sessionTime.get());
        } else throw new NoSuchElementException("세션 시간을 찾을 수 없습니다.");
    }

    public String getGPKIUser(String dn) throws IOException, SQLException, NoSuchElementException {
        // 값이 있는지 먼저 체크해야함.
        return repository.findUserByGpkiDn(dn);
    }
    public Integer dnCheck(String id) throws IOException, SQLException, NoSuchElementException {
        try {
            String dnCheck = repository.findUserById(id)
                    .orElseThrow(Exception::new)
                    .getDn();
            /*
                값이 존재하면 1,
                그렇지 않으면 0
            */
            return (dnCheck != null) ? 1 : 0;
//            return 1;
        } catch (Exception e) {
            // 예외가 발생하면 0 반환
            return 0;
        }
    }

    @Transactional
    public void modifyUserDn(String dn, String id) throws IOException, SQLException, NoSuchElementException {
        // 값이 있는지 먼저 체크해야함.
        repository.updateUserByDn(dn, id);
    }

    public LoginDTO.FindUserIdResponseDTO getUserIdAndRegistTs(LoginDTO.FindUserIdRequestDTO dto) throws IOException, SQLException, NoSuchElementException {
        dto.setEmail(dto.getEmailName() + "@" + dto.getEmailDomain());
        Optional<NoperUserDamo> userInfo = repository.findUserIdByUserInfo(dto);
        LoginDTO.FindUserIdResponseDTO resDTO = new LoginDTO.FindUserIdResponseDTO();
        if (userInfo.isPresent()) {
            NoperUserDamo userDamo = userInfo.get();
            resDTO.setId(userDamo.getUserId());
            resDTO.setDate(userDamo.getRegistTs());
            resDTO.setBool(1);
        } else {
            resDTO.setBool(0);
        }
        return resDTO;
    }

    public LoginDTO.FindUserPwResponseDTO userPwCheck(LoginDTO.FindUserPwRequestDTO dto) throws IOException, SQLException, NoSuchElementException {
        dto.setEmail(dto.getEmailName() + "@" + dto.getEmailDomain());
        Optional<String> userInfo = repository.findUserPwByUserInfo(dto);
        LoginDTO.FindUserPwResponseDTO resDTO = new LoginDTO.FindUserPwResponseDTO();
        if (userInfo.isPresent()) {
            resDTO.setId(userInfo.get());
            resDTO.setBool(1);
        } else {
            resDTO.setBool(0);
        }
        return resDTO;
    }

    @Transactional
    public void resetPassword(LoginDTO.ResetUserPwRequestDTO dto) throws IOException, SQLException, NoSuchElementException {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        repository.updatePassword(dto);
    }
}
