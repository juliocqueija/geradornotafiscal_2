package br.com.itau.calculadoratributos.web.controller;

import br.com.itau.geradornotafiscal.model.Destinatario;
import br.com.itau.geradornotafiscal.model.Documento;
import br.com.itau.geradornotafiscal.model.Item;
import br.com.itau.geradornotafiscal.model.Pedido;
import br.com.itau.geradornotafiscal.model.enums.TipoDocumento;
import br.com.itau.geradornotafiscal.service.GeradorNotaFiscalService;
import br.com.itau.geradornotafiscal.web.controller.GeradorNFController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

@WebMvcTest(controllers = {GeradorNFController.class})
@ContextConfiguration(classes = {GeradorNFController.class})
public class GeradorNFControllerTests {

    @MockBean
    private GeradorNotaFiscalService geradorNotaFiscalService;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Gerar nota fiscal")
    @Test
    public void gerarNotaFiscal() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        Pedido pedido = Pedido
                .builder()
                .idPedido(1L)
                .data(LocalDate.now())
                .valorTotalItens(5000.0)
                .itens(
                        List.of(
                                new Item(1L, "descricao", 500.0, 10)
                        )
                )
                .destinatario(
                        Destinatario
                                .builder()
                                .documentos(
                                        List.of(
                                                new Documento("12345678910", TipoDocumento.CPF)
                                        )
                                )
                                .build()
                )
                .build();

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/pedido/gerarNotaFiscal")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(pedido))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
