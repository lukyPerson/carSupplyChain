package com.dongtech.util;

import com.dongtech.vo.Cart;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author gzl
 * @date 2020-04-16
 * @program: springboot-jsp
 * @description: ${description}
 */
public class CarUtil {

    /**
     * 生成32位UUID主键
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 本算法利用62个可打印字符，通过随机生成32位UUID，由于UUID都为十六进制，所以将UUID分成8组，每4个为一组，
     * 然后通过模62操作，结果作为索引取出字符，这样重复率大大降低。
     *
     * @return
     */
//    public static String getShortUUID() {
//        StringBuffer shortBuffer = new StringBuffer();
//        String uuid = UUID.randomUUID().toString().replace("-", "");
//        for (int i = 0; i < UUID_CHAR_LEN_INT_8; i++) {
//            String str = uuid.substring(i * 4, i * 4 + 4);
//            int x = Integer.parseInt(str, 16);
//            shortBuffer.append(chars[x % 0x3E]);
//        }
//        return shortBuffer.toString();
//    }
}
