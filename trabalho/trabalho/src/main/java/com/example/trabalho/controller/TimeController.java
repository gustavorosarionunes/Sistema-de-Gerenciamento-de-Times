package com.example.trabalho.controller;

import com.example.trabalho.model.Time;
import com.example.trabalho.repository.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Controller
public class TimeController {

    @Autowired
    private TimeRepository timeRepository;

    // Pega o caminho do application.properties
    @Value("${file.upload-dir}")
    private String uploadDir;

    // --- LISTAR (Página Principal) ---
    @GetMapping("/")
    public String listarTimes(Model model) {
        model.addAttribute("times", timeRepository.findAll());
        return "listar"; // -> /resources/templates/listar.html
    }

    // --- VISUALIZAR ---
    @GetMapping("/visualizar/{id}")
    public String visualizarTime(@PathVariable("id") Long id, Model model) {
        Optional<Time> timeOptional = timeRepository.findById(id);
        if (timeOptional.isEmpty()) {
            return "redirect:/";
        }
        model.addAttribute("time", timeOptional.get());
        return "visualizar"; // -> /resources/templates/visualizar.html
    }

    // --- CADASTRAR (Formulário) ---
    // Este é o método que estava faltando e causando o erro 404
    @GetMapping("/cadastrar")
    public String cadastrarForm(Model model) {
        model.addAttribute("time", new Time());
        return "cadastrar"; // -> /resources/templates/cadastrar.html
    }

    // --- CADASTRAR (Ação de Salvar) ---
    @PostMapping("/cadastrar")
    public String salvarTime(@ModelAttribute Time time, @RequestParam("escudoFile") MultipartFile file) {

        // 1. Salvar o arquivo do escudo
        if (!file.isEmpty()) {
            String nomeArquivo = salvarArquivo(file);
            time.setNomeEscudo(nomeArquivo);
        }

        // 2. Salvar o time no banco
        timeRepository.save(time);
        return "redirect:/";
    }

    // --- EDITAR (Formulário) ---
    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable("id") Long id, Model model) {
        Optional<Time> timeOptional = timeRepository.findById(id);
        if (timeOptional.isEmpty()) {
            return "redirect:/";
        }
        model.addAttribute("time", timeOptional.get());
        return "editar"; // -> /resources/templates/editar.html
    }

    // --- EDITAR (Ação de Salvar) ---
    @PostMapping("/editar")
    public String editarTime(@ModelAttribute Time time, @RequestParam("escudoFile") MultipartFile file) {

        // 1. Buscar o time existente no banco para saber o nome do escudo antigo
        Optional<Time> timeExistenteOpt = timeRepository.findById(time.getId());
        if (timeExistenteOpt.isEmpty()) {
            return "redirect:/"; // Ou uma página de erro
        }
        Time timeExistente = timeExistenteOpt.get();

        // 2. Verificar se um NOVO arquivo foi enviado
        if (file != null && !file.isEmpty()) {
            // 2a. Se sim, apagar o arquivo antigo (se existir)
            if (timeExistente.getNomeEscudo() != null && !timeExistente.getNomeEscudo().isEmpty()) {
                excluirArquivo(timeExistente.getNomeEscudo());
            }

            // 2b. Salvar o novo arquivo
            String novoNomeArquivo = salvarArquivo(file);
            time.setNomeEscudo(novoNomeArquivo); // Define o nome do NOVO escudo no objeto

        } else {
            // 2c. Se não, manter o nome do escudo antigo
            time.setNomeEscudo(timeExistente.getNomeEscudo());
        }

        // 3. Salvar o time (com o escudo novo ou o antigo)
        timeRepository.save(time);
        return "redirect:/";
    }

    // --- EXCLUIR ---
    // Este método é novo e corresponde ao botão "Excluir" no listar.html
    @GetMapping("/excluir/{id}")
    public String excluirTime(@PathVariable("id") Long id) {
        Optional<Time> timeOptional = timeRepository.findById(id);
        if (timeOptional.isPresent()) {
            Time time = timeOptional.get();
            // Excluir o arquivo de escudo antes de excluir o registro
            if (time.getNomeEscudo() != null && !time.getNomeEscudo().isEmpty()) {
                excluirArquivo(time.getNomeEscudo());
            }
            timeRepository.deleteById(id);
        }
        return "redirect:/";
    }


    // --- Métodos Auxiliares para Upload ---

    private String salvarArquivo(MultipartFile file) {
        try {
            // Gera um nome de arquivo único para evitar conflitos
            String nomeOriginal = file.getOriginalFilename();
            String extensao = "";
            if(nomeOriginal != null) {
                extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
            }
            String nomeUnico = UUID.randomUUID().toString() + extensao;

            // Define o caminho completo
            Path caminhoUpload = Paths.get(uploadDir);
            Path caminhoArquivo = caminhoUpload.resolve(nomeUnico);

            // Cria o diretório se não existir
            if (!Files.exists(caminhoUpload)) {
                Files.createDirectories(caminhoUpload);
            }

            // Salva o arquivo
            Files.copy(file.getInputStream(), caminhoArquivo, StandardCopyOption.REPLACE_EXISTING);

            return nomeUnico;

        } catch (IOException e) {
            // Trate a exceção adequadamente
            throw new RuntimeException("Não foi possível salvar o arquivo.", e);
        }
    }

    private void excluirArquivo(String nomeArquivo) {
        try {
            Path caminhoArquivo = Paths.get(uploadDir).resolve(nomeArquivo);
            if (Files.exists(caminhoArquivo)) {
                Files.delete(caminhoArquivo);
            }
        } catch (IOException e) {
            // Trate a exceção
            System.err.println("Erro ao excluir arquivo: " + nomeArquivo + " - " + e.getMessage());
        }
    }
}