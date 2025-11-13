package smartLegalApi.infrastructure.document.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;
import smartLegalApi.domain.peticao.entity.Peticao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Serviço para geração de documentos Word (.docx)
 */
@Service
@Slf4j
public class DocumentoWordService {
    
    private static final String UPLOAD_DIR = "./uploads/peticoes";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    /**
     * Gera documento Word a partir da petição
     */
    public String gerarDocumento(Peticao peticao) {
        log.info("Gerando documento Word para petição ID: {}", peticao.getId());
        
        try {
            // Cria o diretório se não existir
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            // Nome do arquivo
            String fileName = String.format(
                "peticao_%d_%s.docx",
                peticao.getId(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
            );
            
            String filePath = uploadPath.resolve(fileName).toString();
            
            // Cria o documento
            try (XWPFDocument document = new XWPFDocument()) {
                
                // Título
                criarTitulo(document, peticao.getTitulo());
                
                // Informações da petição
                criarInformacoes(document, peticao);
                
                // Separador
                criarSeparador(document);
                
                // Conteúdo da petição
                criarConteudo(document, peticao.getConteudo());
                
                // Rodapé com data
                criarRodape(document);
                
                // Salva o documento
                try (FileOutputStream out = new FileOutputStream(filePath)) {
                    document.write(out);
                }
            }
            
            log.info("Documento gerado com sucesso: {}", filePath);
            return filePath;
            
        } catch (IOException e) {
            log.error("Erro ao gerar documento Word", e);
            throw new RuntimeException("Erro ao gerar documento: " + e.getMessage(), e);
        }
    }
    
    /**
     * Cria o título do documento
     */
    private void criarTitulo(XWPFDocument document, String titulo) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        
        XWPFRun run = paragraph.createRun();
        run.setText(titulo.toUpperCase());
        run.setBold(true);
        run.setFontSize(16);
        run.setFontFamily("Arial");
        
        // Espaço após o título
        document.createParagraph();
    }
    
    /**
     * Cria seção de informações
     */
    private void criarInformacoes(XWPFDocument document, Peticao peticao) {
        XWPFParagraph paragraph = document.createParagraph();
        
        XWPFRun run = paragraph.createRun();
        run.setFontSize(10);
        run.setFontFamily("Arial");
        run.setText("Tipo: " + peticao.getTipo().getDescricao());
        run.addBreak();
        run.setText("Status: " + peticao.getStatus().getDescricao());
        run.addBreak();
        run.setText("Data de Criação: " + peticao.getDataCriacao().format(DATE_FORMATTER));
        
        if (peticao.getConteudoGeradoIA() != null) {
            run.addBreak();
            run.setText("✨ Gerado com IA");
            run.setItalic(true);
        }
    }
    
    /**
     * Cria separador visual
     */
    private void criarSeparador(XWPFDocument document) {
        XWPFParagraph separator = document.createParagraph();
        separator.setAlignment(ParagraphAlignment.CENTER);
        
        XWPFRun run = separator.createRun();
        run.setText("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        run.setColor("CCCCCC");
        
        document.createParagraph();
    }
    
    /**
     * Cria o conteúdo principal
     */
    private void criarConteudo(XWPFDocument document, String conteudo) {
        if (conteudo == null || conteudo.isBlank()) {
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText("[Conteúdo não disponível]");
            run.setItalic(true);
            run.setColor("999999");
            return;
        }
        
        // Divide o conteúdo em parágrafos
        String[] paragrafos = conteudo.split("\n\n");
        
        for (String paragrafoTexto : paragrafos) {
            if (paragrafoTexto.trim().isEmpty()) continue;
            
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.BOTH);
            paragraph.setSpacingBefore(200);
            paragraph.setSpacingAfter(200);
            
            XWPFRun run = paragraph.createRun();
            run.setFontSize(12);
            run.setFontFamily("Times New Roman");
            run.setText(paragrafoTexto.trim());
        }
    }
    
    /**
     * Cria rodapé com data
     */
    private void criarRodape(XWPFDocument document) {
        document.createParagraph();
        document.createParagraph();
        
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        
        XWPFRun run = paragraph.createRun();
        run.setFontSize(10);
        run.setFontFamily("Arial");
        run.setText("Documento gerado em: " + LocalDateTime.now().format(DATE_FORMATTER));
        run.setItalic(true);
        run.setColor("666666");
    }
    
    /**
     * Exclui documento do sistema de arquivos
     */
    public void excluirDocumento(String caminhoDocumento) {
        try {
            Path path = Paths.get(caminhoDocumento);
            if (Files.exists(path)) {
                Files.delete(path);
                log.info("Documento excluído: {}", caminhoDocumento);
            }
        } catch (IOException e) {
            log.error("Erro ao excluir documento: {}", caminhoDocumento, e);
        }
    }
}

