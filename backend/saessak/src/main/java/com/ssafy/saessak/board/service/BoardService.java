package com.ssafy.saessak.board.service;


import com.ssafy.saessak.album.domain.Album;
import com.ssafy.saessak.album.repository.AlbumRepository;
import com.ssafy.saessak.board.domain.Board;
import com.ssafy.saessak.board.dto.*;
import com.ssafy.saessak.board.repository.BoardRepository;
import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.domain.Kid;
import com.ssafy.saessak.user.repository.ClassroomRepository;
import com.ssafy.saessak.user.repository.KidRepository;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;
    private final KidRepository kidRepository;
    private final ClassroomRepository classroomRepository;
    private final AlbumRepository albumRepository;
    // crud
    @Transactional
    public Board saveBoard(BoardRequestDto boardRequestDto){
        Kid kid = kidRepository.findById(boardRequestDto.getKidId()).get();
        Classroom classroom = classroomRepository.findById(boardRequestDto.getClassroomId()).get();

        Board saveBoard = Board.builder()
                .kid(kid)
                .classroom(classroom)
                .boardDate(boardRequestDto.getBoardDate())
                .boardContent(boardRequestDto.getBoardContent())
                .boardTemperature(boardRequestDto.getBoardTemperature())
                .boardSleepTime(boardRequestDto.getBoardSleepTime())
                .boardPoopStatus(boardRequestDto.getBoardPoopStatus())
                .boardTall(boardRequestDto.getBoardTall())
                .boardWeight(boardRequestDto.getBoardWeight())
                .build();

        boardRepository.save(saveBoard);

        return saveBoard;
    }

    public BoardDetailDto readBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).get();

        return BoardDetailDto.builder()
                .boardId(board.getBoardId())
                .kidId(board.getKid().getId())
                .classroomId(board.getClassroom().getClassroomId())
                .boardDate(board.getBoardDate())
                .boardContent(board.getBoardContent())
                .boardTemperature(board.getBoardTemperature())
                .boardSleepTime(board.getBoardSleepTime())
                .boardPoopStatus(board.getBoardPoopStatus())
                .boardTall(board.getBoardTall())
                .boardWeight(board.getBoardWeight())
                .boardPath(board.getBoardPath())
                .build();
    }


    @Transactional
    public Long deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).get();
        boardRepository.delete(board);

        return board.getBoardId();
    }
    public List<BoardResponseDto> findByKid(Long kidId){
        Kid kid = kidRepository.findById(kidId).get();
        List<Board> result = boardRepository.findByKid(kid).get();
        List<BoardResponseDto> boardResponseDtoList = new ArrayList<>();
        for(Board board: result){
            List<Album> albumList = albumRepository.findByKidAndAlbumDate(kid,board.getBoardDate()).get();
            // 썸네일 넣는거 query 이게 맞나..?
            String path = null;
            if(! albumList.isEmpty() && ! albumList.get(0).getFileList().isEmpty()){
                path = albumList.get(0).getFileList().get(0).getFilePath();
            }
            BoardResponseDto boardResponseDto = BoardResponseDto.builder()
                    .boardDate(board.getBoardDate())
                    .boardId(board.getBoardId())
                    .boardPath(path)
                    .build();

            boardResponseDtoList.add(boardResponseDto);
        }
        return boardResponseDtoList;
    }

    public BoardDetailDto findByKidAndDate (Long kidId, Date date){
        Kid kid = kidRepository.findById(kidId).get();
        Optional<List<Board>> result = boardRepository.findByKidAndBoardDate(kid,date);
        if (result.isPresent()){
            Board board = result.get().get(0);
            return BoardDetailDto.builder()
                    .boardId(board.getBoardId())
                    .kidId(board.getKid().getId())
                    .classroomId(board.getClassroom().getClassroomId())
                    .boardDate(board.getBoardDate())
                    .boardPath(board.getBoardPath())
                    .boardContent(board.getBoardContent())
                    .boardPoopStatus(board.getBoardPoopStatus())
                    .boardSleepTime(board.getBoardSleepTime())
                    .boardTall(board.getBoardTall())
                    .boardTemperature(board.getBoardTemperature())
                    .boardWeight(board.getBoardWeight())
                    .build();
        }
        else{
            return null;
        }
    }

    public List<PhysicalResponseDto> getPhysicalList (Long kidId, Date startDate,Date endDate){
        Kid kid = kidRepository.findById(kidId).get();
        List<PhysicalResponseDto> physicalResponseDtoList= new ArrayList<>();

        List<Board> result = boardRepository.findByKidAndBoardDateBetween(kid,startDate, endDate).get();
        for( Board board : result){
            PhysicalResponseDto physicalResponseDto = PhysicalResponseDto.builder()
                    .boardDate(board.getBoardDate())
                    .boardWeight(board.getBoardWeight())
                    .boardTall(board.getBoardTall())
                    .build();
            physicalResponseDtoList.add(physicalResponseDto);
        }

        return physicalResponseDtoList;
    }

    public List<ContentResponseDto> getContentList (Long kidId, Date startDate, Date endDate){
        List<ContentResponseDto> contentResponseDtoList = new ArrayList<>();
        Kid kid = kidRepository.findById(kidId).get();
        List<Board> result = boardRepository.findByKidAndBoardDateBetween(kid,startDate,endDate).get();

        for(Board board : result){
            ContentResponseDto contentResponseDto = ContentResponseDto.builder()
                    .boardDate(board.getBoardDate())
                    .boardContent(board.getBoardContent())
                    .build();

            contentResponseDtoList.add(contentResponseDto);
        }


        return contentResponseDtoList;
    }
}
