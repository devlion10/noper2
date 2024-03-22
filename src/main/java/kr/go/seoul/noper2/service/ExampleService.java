package kr.go.seoul.noper2.service;

import kr.go.seoul.noper2.domain.NoticeBbs;
import kr.go.seoul.noper2.repository.ExampleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * CRUD Example Service
 * 객체를 받아올 때, null 체크를 해주세요.
 * Assertion 혹은 Optional 등 사용해주시면 됩니다.
 *
 * @author sunik
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ExampleService {
    private final ExampleRepository repository;

    /*
     * Optional 처리는 if문, orElseThrow 말고도 자유롭게 사용하셔도 됩니다.
     * @param id
     * @return NoticeBbs
     * @throws Exception
     */
    public NoticeBbs findBoard(long id) throws NoSuchElementException, NullPointerException {
        NoticeBbs noticeBbs = repository.findById(id)
            .orElseThrow(NoSuchElementException::new);
        /*
        if (noticeBbs.isPresent()) {
            return noticeBbs.get();
        } else {
            throw new Exception("해당 객체가 존재하지 않습니다.");
        }
         */
        return noticeBbs;
    }
}
