package com.nathanbernal.springweb_foro.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.nathanbernal.springweb_foro.dto.ForoDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ForoService {

    private final WebClient webClient;

    public ForoService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<ForoDto> save(ForoDto foroDto) {
        return webClient.post().uri("/foros").bodyValue(foroDto).retrieve().bodyToMono(ForoDto.class);
    }

    public Flux<ForoDto> getForos() {
        return webClient.get().uri("/foros").retrieve().bodyToFlux(ForoDto.class);
    }

    public Flux<ForoDto> getForosById(Long id) {
        return webClient.get().uri("/foros/"+id).retrieve().bodyToFlux(ForoDto.class);
    }

    public Mono<ForoDto> update(Long id, ForoDto foroDto) {
        return webClient.put().uri("/foros/"+id).bodyValue(foroDto).retrieve().bodyToMono(ForoDto.class);
    }

    public void delete(Long id) {
        webClient.delete().uri("/foros/"+id).retrieve().bodyToMono(ForoDto.class);
    }

}
