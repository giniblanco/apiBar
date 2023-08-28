package atl.academy.services;

import atl.academy.models.FeedbackUserEntity;
import atl.academy.repositories.IFeedbackUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackUserService {

    @Autowired
    private IFeedbackUserRepository feedbackUserRepository;

    public FeedbackUserEntity save(FeedbackUserEntity feedbackUserEntity){
        return feedbackUserRepository.save(feedbackUserEntity);
    }

    public FeedbackUserEntity update(Long id, FeedbackUserEntity feedbackUserEntity){
        FeedbackUserEntity existingFeedback = feedbackUserRepository.findById(id).orElse(null);

        if(existingFeedback != null){
            return feedbackUserRepository.save(feedbackUserEntity);
        }

        return null;
    }

    public boolean delete(Long id){
        if(feedbackUserRepository.existsById(id)){
            feedbackUserRepository.deleteById(id);
            return true;
        } else{
            return false;
        }
    }
}
