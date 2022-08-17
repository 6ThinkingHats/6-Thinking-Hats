package com.ssafy.sixhats.service;

import com.ssafy.sixhats.dao.RoomDAO;
import com.ssafy.sixhats.dao.VideoDAO;
import com.ssafy.sixhats.dto.video.VideoGetResponseDTO;
import com.ssafy.sixhats.dto.video.VideoPostRequestDTO;
import com.ssafy.sixhats.vo.RoomVO;
import com.ssafy.sixhats.vo.VideoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final RoomDAO roomDAO;
    private final VideoDAO videoDAO;

    @Transactional
    public void postVideo(@RequestBody VideoPostRequestDTO videoPostRequestDTO) {
        RoomVO roomVO = roomDAO.findBySessionId(videoPostRequestDTO.getSessionId()).orElse(null);

        VideoVO videoVO = new VideoVO().builder()
                .roomVO(roomVO)
                .videoFileUrl(videoPostRequestDTO.getVideoFileUrl())
                .build();

        videoDAO.save(videoVO);
    }

    @Transactional
    public void patchVideo(Long videoId) {
        VideoVO videoVO = videoDAO.findById(videoId).orElse(null);

        if (videoVO != null && videoVO.isVideoValid()) {
            videoVO.updateVideoValid(false);
        } else {
            throw new NullPointerException("video is not exist");
        }
    }

    @Transactional
    public VideoGetResponseDTO getVideo(Long videoId) {
        VideoVO videoVO = videoDAO.findById(videoId).orElse(null);
        if (videoVO != null) {
            VideoGetResponseDTO videoGetResponseDTO = new VideoGetResponseDTO().builder()
                    .videoFileUrl(videoVO.getVideoFileUrl())
                    .videoValid(videoVO.isVideoValid())
                    .videoId(videoVO.getVideoId())
                    .build();
            return videoGetResponseDTO;
        } else {
            throw new NullPointerException("video info is not found");
        }
    }

    @Transactional
    public List<VideoGetResponseDTO> getVideoList(Long roomId) {
        RoomVO roomVO = roomDAO.findById(roomId).orElse(null);
        if (roomVO == null) {
            throw new NullPointerException("room is not found");
        }

        List<VideoVO> videoVoList = new ArrayList<>();
        videoVoList = videoDAO.findAllByRoomVO(roomVO);
        if(videoVoList == null ) {
            throw new NullPointerException("video is not found");
        }

        List<VideoGetResponseDTO> videoDtoList = new ArrayList<>();
        for (VideoVO videoVO : videoVoList) {
            videoDtoList.add(new VideoGetResponseDTO().builder()
                    .videoFileUrl(videoVO.getVideoFileUrl())
                    .videoValid(videoVO.isVideoValid())
                    .videoId(videoVO.getVideoId())
                    .build());
        }
        return videoDtoList;

    }
}
