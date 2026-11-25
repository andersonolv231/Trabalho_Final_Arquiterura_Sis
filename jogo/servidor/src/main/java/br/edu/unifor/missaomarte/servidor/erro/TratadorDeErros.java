package br.edu.unifor.missaomarte.servidor.erro;

import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {
    private static final Logger LOG = LoggerFactory.getLogger(TratadorDeErros.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> validacao(MethodArgumentNotValidException excecao) {
        Map<String, String> campos = new LinkedHashMap<>();
        excecao.getBindingResult().getFieldErrors()
                .forEach(erro -> campos.putIfAbsent(erro.getField(), erro.getDefaultMessage()));
        return resposta(HttpStatus.BAD_REQUEST, ErroResposta.comCampos(400, "VALIDACAO",
                "Dados inválidos", campos));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResposta> corpoInvalido(HttpMessageNotReadableException excecao) {
        return resposta(HttpStatus.BAD_REQUEST, ErroResposta.de(400, "CORPO_INVALIDO",
                "O corpo da requisição não é um JSON válido"));
    }

    @ExceptionHandler(ConflitoException.class)
    public ResponseEntity<ErroResposta> conflito(ConflitoException excecao) {
        return resposta(HttpStatus.CONFLICT, ErroResposta.de(409, "CONFLITO", excecao.getMessage()));
    }

    @ExceptionHandler(NaoAutorizadoException.class)
    public ResponseEntity<ErroResposta> naoAutorizado(NaoAutorizadoException excecao) {
        return resposta(HttpStatus.UNAUTHORIZED, ErroResposta.de(401, "NAO_AUTORIZADO", excecao.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> inesperado(Exception excecao) {
        if (excecao instanceof ErrorResponse respostaSpring) {
            int codigo = respostaSpring.getStatusCode().value();
            return ResponseEntity.status(codigo).body(ErroResposta.de(codigo, "HTTP_" + codigo,
                    excecao.getMessage()));
        }
        LOG.error("Erro inesperado: {}", excecao.getClass().getSimpleName());
        return resposta(HttpStatus.INTERNAL_SERVER_ERROR, ErroResposta.de(500, "ERRO_INTERNO",
                "Erro inesperado no servidor"));
    }

    private static ResponseEntity<ErroResposta> resposta(HttpStatus status, ErroResposta corpo) {
        return ResponseEntity.status(status).body(corpo);
    }
}
