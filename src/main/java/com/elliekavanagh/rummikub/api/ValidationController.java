package com.elliekavanagh.rummikub.api;

import com.elliekavanagh.rummikub.api.dto.MeldValidationRequest;
import com.elliekavanagh.rummikub.api.dto.MeldValidationResponse;
import com.elliekavanagh.rummikub.api.dto.TileDto;
import com.elliekavanagh.rummikub.model.Color;
import com.elliekavanagh.rummikub.model.Meld;
import com.elliekavanagh.rummikub.model.MeldType;
import com.elliekavanagh.rummikub.model.Tile;
import com.elliekavanagh.rummikub.rules.MeldRulesEngine;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class ValidationController {

    private final MeldRulesEngine engine = new MeldRulesEngine();

    @PostMapping("/melds/validate")
    public ResponseEntity<MeldValidationResponse> validate(@RequestBody MeldValidationRequest request) {
        Meld meld = toMeld(request);
        boolean valid = engine.isValid(meld);
        return ResponseEntity.ok(new MeldValidationResponse(valid));
    }

    private Meld toMeld(MeldValidationRequest request) {
        MeldType type = MeldType.valueOf(request.getType().toUpperCase());
        List<Tile> tiles = request.getTiles().stream().map(this::toTile).collect(Collectors.toList());
        return new Meld(type, tiles);
    }

    private Tile toTile(TileDto dto) {
        boolean isJoker = dto.getJoker() != null && dto.getJoker();

        if (isJoker) {
            // Assumes your Tile model supports joker via nulls or a special constructor.
            // If your Tile has Tile(Color, int) only, tell me and I’ll match YOUR Tile API.
            return Tile.joker();
        }

        Color color = Color.valueOf(dto.getColor().toUpperCase());
        int value = dto.getValue();
        return new Tile(color, value);
    }
}
