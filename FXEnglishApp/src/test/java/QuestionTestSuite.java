/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.dht.pojo.Choice;
import com.dht.pojo.Question;
import com.dht.services.QuestionServices;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 *
 * @author admin
 */
public class QuestionTestSuite {
    private static QuestionServices s = new QuestionServices();
    
    @Test
    public void test() {
       
        try {
            List<Question> questions = s.getQuestions(2);
            Assertions.assertEquals(questions.size(), 2);
            
            questions.forEach(s1 -> System.out.println(s1));
        } catch (SQLException ex) {
            Logger.getLogger(QuestionTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testCates() {
        try {
            List<Question> questions = s.getQuestions(5);
            for (var x: questions)
                Assertions.assertNotEquals(x.getCategoryId(), 0);
        } catch (SQLException ex) {
            Logger.getLogger(QuestionTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testChoice() {
        try {
            List<Question> questions = s.getQuestions(5);
            for (var x: questions) {
                List<Choice> choices = s.getChoices(x.getId());
                Set<Choice> tmp = new HashSet<>(choices);
                
                Assertions.assertEquals(choices.size(), tmp.size());
            }
                
        } catch (SQLException ex) {
            Logger.getLogger(QuestionTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testRightNumberOfChoice() {
        try {
            List<Question> questions = s.getQuestions(5);
            for (var x: questions) {
                List<Choice> choices = s.getChoices(x.getId());
                Assertions.assertEquals(choices.size(), 4);
                
                Assertions.assertTrue(choices.stream().filter(c -> c.isCorrect() == true).count() == 1);
            }
                
        } catch (SQLException ex) {
            Logger.getLogger(QuestionTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
