package org.geeksword.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "remote", url = "https://")
public interface RemoteService {

    @GetMapping(value = "www.baidu.com/")
    String bd();

    @GetMapping(value = "localhost:8080/test")
    ResponseDemo test();

}
