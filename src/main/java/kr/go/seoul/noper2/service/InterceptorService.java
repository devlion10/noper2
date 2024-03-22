package kr.go.seoul.noper2.service;

import kr.go.seoul.noper2.dto.InterceptorDTO;
import kr.go.seoul.noper2.repository.InterceptorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class InterceptorService {
    private final InterceptorRepository repository;

    public InterceptorDTO getMyPermission(ConcurrentMap<String, Object> map) throws NoSuchElementException {
        return repository.findByPermission(map).orElseThrow(NoSuchElementException::new);
    }
}
