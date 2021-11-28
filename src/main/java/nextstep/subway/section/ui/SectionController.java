package nextstep.subway.section.ui;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import nextstep.subway.section.application.SectionService;
import nextstep.subway.section.dto.SectionRequest;

@RestController
public class SectionController {
    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @PostMapping("/{lineId}/sections")
    public ResponseEntity<Void> addSection(@PathVariable Long lineId, @RequestBody SectionRequest sectionRequest) {
        long sectionId = sectionService.saveSection(lineId, sectionRequest);
        return ResponseEntity.created(URI.create("/" + sectionId + "/sections"))
            .build();
    }
}
