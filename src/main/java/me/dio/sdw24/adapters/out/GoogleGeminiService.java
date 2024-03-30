package me.dio.sdw24.adapters.out;

import feign.RequestInterceptor;
import me.dio.sdw24.domain.ports.GenerativeAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "geniApi", url = "${openai.base-url}", configuration = GoogleGeminiService.Config.class)
public interface GoogleGeminiService extends GenerativeAiApi {

    @PostMapping("/v1/chat/completions")
    OpenAiCHatCompletionResp chatCompletion(OpenAiChatCompletionReq req);

    default String generateContent(String objective, String context) {
         String model = "gpt-3.5-turbo";
         List<Message> messages = List.of(
                 new Message("system", objective),
                 new Message("user", context)
         );
         OpenAiChatCompletionReq req = new OpenAiChatCompletionReq(model, messages);

        OpenAiCHatCompletionResp resp = chatCompletion(req);

        return resp.choices().get(0).message().content();
    }

    record OpenAiChatCompletionReq(String model, List<Message> messages) { }
    record OpenAiCHatCompletionResp(List<Choice> choices){}
    record Message(String role, String content) { }
    record Choice(Message message){}


    class Config{
        @Bean
        public RequestInterceptor apiKeyRequestInterceptor(@Value("${openai.api-key}") String apiKey){
            return requestTemplate -> requestTemplate.header(HttpHeaders.AUTHORIZATION,
                    "Bearer %s".formatted(apiKey));
        }
    }

}
