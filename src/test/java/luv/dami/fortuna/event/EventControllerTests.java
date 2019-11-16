package luv.dami.fortuna.event;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
public class EventControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventRepository eventRepository;

    @MockBean
    private EventParticipantRepository eventParticipantRepository;

    @Test
    public void testSaveEvent() throws Exception {
        mockMvc.perform(post("/events"))
                .andExpect(status().isOk());
    }

    @Test
    public void testParticipateEvent() throws Exception {
        mockMvc.perform(post("/events/1/participate"))
                .andExpect(status().isOk());
    }
}
