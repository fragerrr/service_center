package kz.justdika.service_center.service;

import kz.justdika.service_center.config.ServiceCenterTestConfiguration;
import kz.justdika.service_center.exception.ParamNotCorrectException;
import kz.justdika.service_center.model.dto.claim.ClaimCreateRequest;
import kz.justdika.service_center.model.event.ClaimCreatedEvent;
import kz.justdika.service_center.repository.ClaimHistoryRepository;
import kz.justdika.service_center.repository.ClaimRepository;
import kz.justdika.service_center.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@SpringBootTest(classes = ServiceCenterTestConfiguration.class)
class ClaimServiceTest {
    @Autowired
    private ClaimService sut;

    @Autowired
    private ClaimRepository claimRepository;

    @Captor
    private ArgumentCaptor<ClaimCreatedEvent> eventCapture;

    @MockitoBean
    private ClaimHistoryService claimHistoryService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClaimHistoryRepository claimHistoryRepository;

    @BeforeEach
    void setUp() {
        claimHistoryRepository.deleteAll();
        claimRepository.deleteAll();
        clientRepository.deleteAll();
    }

    @Test
    void testCreate(){
        var request = new ClaimCreateRequest();
        request.setDescription("TEST");
        request.setClientId(null);
        request.setPhoneNumber("87771234567");

        var result = sut.create(request);
        var claims = claimRepository.findAll();

        assertThat(claims)
                .isNotEmpty()
                .hasSize(1);
        var claim = claims.getFirst();
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(claim.id());
        assertThat(result.message()).isEqualTo("Claim create successfully");

        verify(claimHistoryService, times(1)).claimCreated(eventCapture.capture());
        assertThat(eventCapture.getValue().claimId()).isEqualTo(String.valueOf(result.id()));
    }



    @Test
    void testCreateIfPhoneNumberIsNull(){
        var request = new ClaimCreateRequest();
        request.setDescription("TEST");

        ParamNotCorrectException exception = assertThrows(ParamNotCorrectException.class,
             () -> sut.create(request));
        assertEquals("Номер телефона обязателен к заполнению", exception.getMessage());

        var claims = claimRepository.findAll();
        assertThat(claims).isEmpty();
    }


}