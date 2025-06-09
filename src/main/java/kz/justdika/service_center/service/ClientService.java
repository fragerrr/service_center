package kz.justdika.service_center.service;

import kz.justdika.service_center.exception.ParamNotCorrectException;
import kz.justdika.service_center.model.dto.client.ClientRequest;
import kz.justdika.service_center.model.entity.ClientEntity;
import kz.justdika.service_center.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientEntity getByPhoneNumber(String phoneNumber){
        return clientRepository.findByPhoneNumber(phoneNumber)
                .orElseGet(() -> saveClient(new ClientRequest(phoneNumber)));
    }

    public ClientEntity saveClient(ClientRequest request){
        if(request == null || request.phoneNumber == null){
            throw new ParamNotCorrectException("Номер телефона обязателен к заполнению");
        }

        var client = new ClientEntity()
                .firstname(request.firstName)
                .lastname(request.lastName)
                .phoneNumber(request.phoneNumber);

        return clientRepository.save(client);
    }
}
