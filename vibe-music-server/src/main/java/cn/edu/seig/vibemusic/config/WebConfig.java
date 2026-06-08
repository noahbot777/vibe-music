package cn.edu.seig.vibemusic.config;

import cn.edu.seig.vibemusic.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 登录接口和注册接口不拦截
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**") // 拦截所有请求
                .excludePathPatterns(
                        "/admin/login", "/admin/logout", "/admin/register",
                        "/user/login", "/user/logout", "/user/register",
                        "/user/sendVerificationCode", "/user/resetUserPassword",
                        "/banner/getBannerList",
                        "/playlist/getAllPlaylists", "/playlist/getRecommendedPlaylists", "/playlist/getPlaylistDetail/**",
                        "/artist/getAllArtists", "/artist/getArtistDetail/**",
                        "/song/getAllSongs", "/song/getRecommendedSongs", "/song/getSongDetail", "/song/getSongDetail/**",
                        "/song/url/v1",
                        "/static/**",
                        // 以下是完整路径（包含上下文路径）
                        "/api/admin/login", "/api/admin/logout", "/api/admin/register",
                        "/api/user/login", "/api/user/logout", "/api/user/register",
                        "/api/user/sendVerificationCode", "/api/user/resetUserPassword",
                        "/api/banner/getBannerList",
                        "/api/playlist/getAllPlaylists", "/api/playlist/getRecommendedPlaylists", "/api/playlist/getPlaylistDetail/**",
                        "/api/artist/getAllArtists", "/api/artist/getArtistDetail/**",
                        "/api/song/getAllSongs", "/api/song/getRecommendedSongs", "/api/song/getSongDetail", "/api/song/getSongDetail/**",
                        "/api/song/url/v1",
                        "/api/static/**");
    }

    // 静态资源映射配置
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 前端访问 /static/** → 指向本地资源文件夹
        registry.addResourceHandler("/static/**")
                .addResourceLocations("file:D:/GitHub开源项目/vibe-music-data/");
    }
}
