package com.back.domain.answer.answer.service;

import com.back.domain.answer.answer.entity.Answer;
import com.back.domain.answer.answer.repository.AnswerRepository;
import com.back.domain.question.question.entity.Question;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

  public Answer modify(int id, String editContent) {
        Optional<Answer> optionalAnswer = answerRepository.findById(id);

        if (optionalAnswer.isPresent()) {
            Answer answer = optionalAnswer.get();
            answer.setContent(editContent);
            return answerRepository.save(answer);
        }else {
            throw new RuntimeException(id + "번 답변이 존재하지 않습니다.");
        }
    }
  
    public Answer write(String content, Question question) {
        Answer answer = new Answer(content, question);
        answerRepository.save(answer);
        return answer;
    }

    public void update(String content, int id) {
        Answer answer = answerRepository.findById(id).stream().findFirst().orElseThrow(() -> new EntityNotFoundException("Answer not found"));
        answer.setContent(content);
        answerRepository.save(answer);
    }

    public Answer findById(int id){
        return answerRepository.findById(id).orElseThrow(() -> new RuntimeException("질문 불러오기 오류"));
    }
      
    public void delete(int id) {
        Answer answer = findById(id);
        answerRepository.delete(answer);
   }
}
