package org.example.datafulldisplay.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class ImageUploadService {

    private final WebClient webClient = WebClient.builder().build();

    /***
     * 使用webclient将图片转发到pythonapi进行行人识别
     * @param file
     * @return
     * @throws Exception
     */
    public Mono<Long> uploadImage(MultipartFile file) throws Exception {
        String pythonApiUrl = "http://localhost:8510/predict/person";//行人识别 python api，请自行修改

        return webClient.post()
                .uri(pythonApiUrl)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData("file", file.getResource()))
                .retrieve()
                .bodyToMono(Long.class);
    }

    public Mono<Long> uploadImageUrl(String imageUrl) throws Exception {
        String pythonApiUrl = "http://localhost:8510/predict/fire";//火情识别python api，请自行修改

        return webClient.post()
                .uri(pythonApiUrl)
                .bodyValue(imageUrl)  // 将图片URL作为请求体发送
                .retrieve()
                .bodyToMono(Long.class);  // 接收Long类型的返回值
    }
}
