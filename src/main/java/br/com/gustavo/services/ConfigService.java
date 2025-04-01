package br.com.gustavo.services;

import br.com.gustavo.repositories.ConfigRespository;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {

    private final ConfigRespository respository;

    public ConfigService(ConfigRespository respository) {
        this.respository = respository;
    }

    public String findValueByKey(String key) {
        return respository.findValueByKey(key);
    }
}
