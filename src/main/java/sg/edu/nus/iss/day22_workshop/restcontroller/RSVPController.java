package sg.edu.nus.iss.day22_workshop.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sg.edu.nus.iss.day22_workshop.model.RSVP;
import sg.edu.nus.iss.day22_workshop.repo.RsvpRepoImpl;

@RequestMapping("/api/rsvps")
@RestController
public class RSVPController {

    @Autowired
    RsvpRepoImpl rsvpRepo;

    @GetMapping(path = { "/", "/home" }, produces = "application/json")
    public ResponseEntity<List<RSVP>> getAllRSVP() {

        List<RSVP> listRSVP = new ArrayList<RSVP>();

        listRSVP = rsvpRepo.findALL();

        if (listRSVP.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(listRSVP, HttpStatus.OK);
        }
    }

    @GetMapping
    public ResponseEntity<List<RSVP>> getRSVPByName(@RequestParam("name") String name) {

        List<RSVP> listOfRSVP = rsvpRepo.findByName(name);

        if (listOfRSVP.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping("/")
    public ResponseEntity<String> saveRSVP(@RequestBody RSVP rsvp) {
        try {
            RSVP r = rsvp;
            Boolean saved = rsvpRepo.save(r);

            if (saved) {
                return new ResponseEntity<>("RSVP record created successfully.", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("RSVP record failed to create.", HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>("RSVP record created successfully.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRSVP(@PathVariable("id") Integer id, @RequestBody RSVP rsvp) {
        
        RSVP r = rsvpRepo.findById(id);

        Boolean result = false;
        if (r != null) {
            result = rsvpRepo.update(rsvp);
        }

        if (result) {
            return new ResponseEntity<>("RSVP record created successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("RSVP record failed to create.", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getRSVPCount() {
        Integer rsvpCount = rsvpRepo.countAll();

        return new ResponseEntity<>(rsvpCount, HttpStatus.OK);
    }

}
