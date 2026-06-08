package cn.edu.seig.vibemusic.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 歌曲URL VO
 */
@Data
public class SongUrlVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 歌曲id
     */
    private Long id;

    /**
     * 歌曲播放URL
     */
    private String url;
}
