package br.com.gustavo.services;

import br.com.gustavo.repositories.ConfigRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {

    @Autowired
    private ConfigRespository respository;

    public String findValueByKey(String key) {
        return respository.findValueByKey(key);
    }
}
