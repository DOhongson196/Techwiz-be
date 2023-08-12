package com.nosz.techwiz.service;

import com.nosz.techwiz.dto.PlayerDto;
import com.nosz.techwiz.entity.Player;
import com.nosz.techwiz.exception.CustomException;
import com.nosz.techwiz.respository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerService {
    @Autowired
    private  PlayerRepository playerRepository;

    @Autowired
    private  FileStorageService fileStorageService;

    public List<PlayerDto> getAllPlayers() {
        List<Player> players = playerRepository.findAll();
        return players.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Page<PlayerDto> getPlayers(Pageable pageable) {
        Page<Player> playerPage = playerRepository.findAll(pageable);
        return playerPage.map(this::convertToDto);
    }


    public PlayerDto getPlayerById(Long id) {
        Optional<Player> playerOptional = playerRepository.findById(id);
        return playerOptional.map(this::convertToDto).orElse(null);
    }


    public PlayerDto createPlayer(PlayerDto playerDto) {
        Player player = convertToEntity(playerDto);
        Player savedPlayer = playerRepository.save(player);
        return convertToDto(savedPlayer);
    }


    public PlayerDto updatePlayer(Long id, PlayerDto playerDto) {
        Optional<Player> playerOptional = playerRepository.findById(id);
        if (playerOptional.isPresent()) {
            Player playerToUpdate = playerOptional.get();
            playerToUpdate.setName(playerDto.getName());
            playerToUpdate.setNumber(playerDto.getNumber());
            playerToUpdate.setPosition(playerDto.getPosition());
            playerToUpdate.setNational(playerDto.getNational());
            playerToUpdate.setDateOfBirth(playerDto.getDateOfBirth());
            playerToUpdate.setHeight(playerDto.getHeight());
            playerToUpdate.setWeight(playerDto.getWeight());
            String preImage = playerToUpdate.getImage();
            if (playerDto.getImage() != null) {
                if (preImage != null) {
                    fileStorageService.deletePlayerImageFile(preImage);
                }
                playerToUpdate.setImage(playerDto.getImage());
            }
            Player updatedPlayer = playerRepository.save(playerToUpdate);
            return convertToDto(updatedPlayer);
        }else{
            throw new CustomException("Player not found");
        }
    }


    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }

    private PlayerDto convertToDto(Player player) {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setId(player.getId());
        playerDto.setName(player.getName());
        playerDto.setPosition(player.getPosition());
        playerDto.setNational(player.getNational());
        playerDto.setImage(player.getImage());
        playerDto.setWeight(player.getWeight());
        playerDto.setDateOfBirth(player.getDateOfBirth());
        playerDto.setHeight(player.getHeight());
        playerDto.setNumber(player.getNumber());
        return playerDto;
    }

    private Player convertToEntity(PlayerDto playerDto) {
        Player player = new Player();
        player.setName(playerDto.getName());
        player.setNational(playerDto.getNational());
        player.setPosition(playerDto.getPosition());
        player.setDateOfBirth(playerDto.getDateOfBirth());
        player.setImage(playerDto.getImage());
        player.setHeight(playerDto.getHeight());
        player.setWeight(playerDto.getWeight());
        player.setNumber(playerDto.getNumber());
        return player;
    }
}
