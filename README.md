# Photocyl
Final project of OOP

- Link para o que falamos que iamos implementar:

        https://www.overleaf.com/9610901vpynnzdrykcs#/34927327/

- Link para o relátorio: 

        https://www.overleaf.com/9308801srxcvkdjrfdd
        
        
# TODO

  - Criar manual
      
  - Achar uma maneira de centralizar a imagem no centro da tela   
      
        - (Cruz) Achar uma maneira de usar o tamanho nativo da tela
        
  - Criar o Menu da direita (color picker)

  - (M) Associar função de processamento com seu filtro 
  
  - Mudar a imagem mostrada na tela conforme editada
             
  - Descobrir como que Da zoom 
  
  - (B) Botão new para criar uma imagem nova com a cor e a dimensão que o usuário desejar
        
  - Colocar uma versão de copyright na about page (Cruz, você manja disso)
    
  - (B) Criar uma classe de exceções para as exceções de run time (ex: não existe arquivo fxml)
  
  - Recorte
        
        - Clicar e pegar as coordenadas do pixel
        
  - Rotação
             

#Tasks done

   - Brunão:
        - 
        - Criar relatório
        - Transformar a BufferedImage para Image
        - Permitir um undo de edição
        - Fazer Save de arquivo
        - SaveAs
        - Close
        
   - Cyrrilo:
        -
        - Filtros
   - Cruz:
        - 
        - 
   - Marcello:
        -
        - Layout principal
        - Menu do layout principal
        - Abrir imagem 
        - Mostrar imagem
        - Nova window para filtros
        - About page
        - CSS do about
        - Layout da janela de filtros
        - Mostrar a imagem principal na janela de filtros
        

# Functions
  - [ ] Crop
    - [ ] Seleção retangular
    - [ ] Seleção direta (com o mouse)
    
  - Distorção e Redimensionalização 
    - [X] Espelhar (vetical, horizontal e diagonalmente)
    - [ ] Esticar (vertical e horizontal)
    - [ ] Resize
    - [ ] Rotação
    
  - [X] Filters
    - [X] Mundanças de canais HSL e RGB (dá para montar filtros tipo instagram com isso)
    - [X] Realce de Imagens
    - [X] Inclusão de ruído
    - [X] Convolução de matrizes: Blur, sharp
  
  - [ ] Desenho
    - [ ] Brush (desenhar com o mouse)
    - [ ] Bucket
    - [ ] Color selector (menu e por pixel na imagem)
 
  - [ ] Extra
    - [ ] Filtros pré-prontos (e.g. foto envelhecida)
    - [ ] Prioridade de cada layer e sobreposição desses
    - [ ] Seleção por isomorfismo (mesma cor, textura, etc)
    - [ ] Remoção de ruído
    - [ ] Formas geométricas e Adesivos padrão
    - [ ] Abrir uma imagem remotamente
    - [ ] Shortcuts
   
# Coding directives
  - Java version: Java 8
  
  - GUI/User interface: JavaFX implementado com JavaFX Scene Builder (funciona integrado graficamente ao Intelij)
    - Para o background de toda janela principal, usar cinza(#353535)
    - Link para versão paga de graça: https://www.jetbrains.com/student/
      
 - GitHub
    - Usar os comentários na hora de commit.
    - Evitar commits gigantes, preferível mandar pouco a pouco
    - Um branch por feature, no mínimo (por exemplo, um branch para filtros)
    - Dar merge sempre que tudo funcionar de um determinado subbranch.
    - PS: Intellij tem plugin de GitHub que é fantástico :D
    
 - Convenção de nomes
   - Classes: Todas as palavras começando com maiúscula. ex: ContaBancariaDoBanco
   - Variáveis: 1ª palavra minúscula, outras começam com maiúscula (camelCase). ex: contaBancariaDoBanco
   
 - Acrescentar "TODO" no que faltar num código antes de ser upado para o Git. O Intellij depois mostra bonitinho todos os TODOs
   
 - Sempre tratamos a exceção no nível em que é possível, não passamos ela adiante se podemos tratar dentro daquela função
 
 - Linhas com menos de 80 caracteres
 
 - Javadoc em ingles
 
 - Comentarios/nomes/funcoes em inglês
 
 - Usar o o MAVEN para configurar o projeto
 
 - Adicionar tasks conforme achar necessário
 
 - Quando a task acabar, tirem ela da lista de tasks e mudam pra tasks done
   
 - Estruturação das packages:
    - Src
      - GUI
        - About
        - Filter
        - Main
      - ioputs
      - testCases
      - imageProcessing
 

 - Funcoes que editam a imagem
 
    - Retornam uma imagem nova
    
    - Nao mudam o que foi passado
    
    - Sugerem um shortcut


# Coisas que necessitam ser implementadas. 
Colocar um check junto com onde foi implementado (para sabermos o que mencionar na hora de fazer o relatório).

- Exemplo: 
    - [X] Threads (Na classe X)
    
- [ ] Exceções -> try-catch-finally ()
  - [ ] Checked -> Compilation time exception
  - [ ] Unchecked -> Run-time exception
  
- [ ] Override -> toString, equals, etc ()

- [ ] Polimorfismo
  - [ ] Extends
  - [ ] Protected
  - [X] Static
  - [ ] Abstract
  
 - [X] Packages
        
        (Na própria estrutração do projeto inteiro.)
 
 - [ ] Test cases -> vamos usar o JUNIT 5
   - [ ] @Test
   - [ ] @assertEquals
   - [ ] @beforeClass
   
  - [ ] Iteradores
  
  - [ ] Algumas estruturas da Java.util (ex: Set, Map, Vector, ArrayList, etc)
        - [X] ArrayList (undo)
  
  - [ ] Serialização (guardar objetos)
  
  - [ ] Threads (talvez....)
  
  - [X] Interfaces
    
        Usamos o JavaFX com o Scece Builder, fxml e css.

    
# Intellij no lab
Installar normalmente

Usar esse link para mudar alguns paths: https://stackoverflow.com/questions/4809214/is-there-an-easy-way-to-put-my-entire-installation-of-intellij-on-a-usb-stick

Copiar a pasta para o ICMC Drive no fim do dia (não funciona rodar da pasta do drive, tem que copiar pro PC).

Para usar integrado com Git:

    - Baixar git https://git-scm.com/download/win
    
    - Setar onde o git foi installado https://stackoverflow.com/questions/26077449/i-cant-find-my-git-exe-file-in-my-github-folder

Para setar o SDK, vai em computer, Program Files, java, jdk 1.8. Não pode ser a (x86) tem que ser a padrão
