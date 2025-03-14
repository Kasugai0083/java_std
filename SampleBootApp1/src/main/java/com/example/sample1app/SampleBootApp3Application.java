package com.example.sample1app;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API コントローラー
 * `@RestController` を付けることで、このクラスが HTTP リクエストを処理することを示す
 */
//@RestController
public class SampleBootApp3Application {

    /**
     * ルートパス ("/") にアクセスした際に HTML ページを返す
     *
     * @param request  クライアントからの HTTP リクエスト情報
     * @param response サーバーからの HTTP レスポンス情報
     * @return HTML コンテンツを文字列として返す
     */
    @RequestMapping("/")
    public String index(
        HttpServletRequest request,  // クライアントのリクエスト情報（IP アドレス、ヘッダーなど）
        HttpServletResponse response // サーバーからのレスポンス情報
    ) {
        // レスポンスのコンテンツタイプを "text/html" に設定（HTML を返すため）
        response.setContentType(MediaType.TEXT_HTML_VALUE);

        // 返却する HTML コンテンツ
        String content = """
                <html>
                    <head>
                        <title>Sample App</title>
                    </head>
                    <body>
                        <h1>Sample App</h1>
                        <p>This is Sample app page!</p>
                    </body>
                </html>
                """;

        // HTML を文字列として返す
        return content;
    }
}
