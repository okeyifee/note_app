package com.example.okeyifee.service;

import com.example.okeyifee.dto.NoteDTO;
import com.example.okeyifee.models.Note;
import com.example.okeyifee.payload.ApiResponse;
import com.example.okeyifee.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;

import static com.example.okeyifee.utils.Serializer.deserialize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(CustomParameterResolver.class)
//@ActiveProfiles("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest
public class NoteTest{

    @MockBean
    private NoteRepository noteRepository;

    @Autowired
    private NoteService noteService;


    List<Note> notes;
    List<Note> notes2;
    List<Note> notes3;
    NoteDTO noteDTO;

    @BeforeEach
    void setUp() {
        noteDTO = new NoteDTO();

        noteDTO.setTitle("transfers");
        noteDTO.setId(32L);
        noteDTO.setSubtitle("us open");
        noteDTO.setCategory("tennis");
        noteDTO.setContent("rafeal nadal ");
        noteDTO.setDeleted(false);

        try {
           notes =  (List<Note>) deserialize("src/test/java/com/example/okeyifee/utils/my_notes_all.data");
           notes3 = (List<Note>) deserialize("src/test/java/com/example/okeyifee/utils/my_notes_title.data");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Test
    @DisplayName("Should return all user's notes with deleted: false")
    public void returnAllNotes() {

        when(noteRepository.findAllNotes()).thenReturn(notes);

        ResponseEntity<ApiResponse> data =  noteService.findAllNotes();

        System.out.println("data:  " + data);
        assertAll(
                () -> assertNotNull(data.getBody().getData()),
                () -> assertEquals(200, data.getStatusCodeValue()),
                () -> assertTrue(data.getBody().getData().toString().toLowerCase().contains("title")),
                () -> assertTrue(data.getBody().getData().toString().toLowerCase().contains("subtitle")),
                () -> assertTrue(data.getBody().getData().toString().toLowerCase().contains("content")),
                () -> assertTrue(data.getBody().getData().toString().toLowerCase().contains("createdat"))
        );
    }

    @Test
    @DisplayName("Should return all user's notes with title: transfers")
    public void findNoteUsingTitle() {

        System.out.println(noteDTO);
        noteDTO.setTitle("transfers");
        when(noteRepository.findNoteByTitle(noteDTO.getTitle())).thenReturn(notes3);

        ResponseEntity<ApiResponse> data =  noteService.findNoteUsingTitle(noteDTO);
        System.out.println("data:   " + data);

        System.out.println("data:  " + data);
        assertAll(
                () -> assertNotNull(data.getBody().getData()),
                () -> assertEquals(200, data.getStatusCodeValue()),
                () -> assertTrue(data.getBody().getData().toString().toLowerCase().contains("title")),
                () -> assertTrue(data.getBody().getData().toString().toLowerCase().contains("subtitle")),
                () -> assertTrue(data.getBody().getData().toString().toLowerCase().contains("content")),
                () -> assertTrue(data.getBody().getData().toString().toLowerCase().contains("createdat"))
        );
    }
}

class CustomParameterResolver implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext,
                                     ExtensionContext extensionContext) throws ParameterResolutionException {
        return (parameterContext.getParameter().getType() == NoteTest.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext,
                                   ExtensionContext extensionContext) throws ParameterResolutionException {
        return new NoteTest();
    }
}
