# Photocyl
Projeto final de POO

# Coisas que necessitam ser implementadas. Colocar um check junto com onde foi implementado.
- Exemplo: [] Threads (Na classe X)
- Exceções -> try-catch-finally ()
  - Checked -> Compilation time exception
  - Unchecked -> Run-time exception
- [] Override -> toString, equals, etc ()
- Polimorfismo
  - Extends
  - Protected
  - Static
  - Abstract
  
 - Packages
 - Test cases -> vamos usar o JUNIT 5
   - @Test
   - @assertEquals
   - @beforeClass
  - Iteradores
  - Algumas estruturas da Java.util (ex: Set, Map, Vector, ArrayList, etc)
  - Serialização (guardar objetos)
  - Threads (talvez....)
  - Interfaces


# Rules to code
 - Convenção de nomes
   - Classes: Todas as palavras começando com maiúscula. ex: ContaBancariaDoBanco
   - Variáveis: 1ª palavra minúscula, outras começam com maiúscula (camelCase). ex: contaBancariaDoBanco
 - Sempre tratamos a exceção no nível em que é possível, não passamos ela adiante se podemos tratar dentro daquela função
 - Linhas com menos de 80 caracteres
 - Javadoc em ingles
 - Comentarios/nomes/funcoes em inglês
 - Usar o o MAVEN para configurar o projeto
 - Adicionar tasks conforme achar necessário
 - Aps acabar um task, adicionar sua inicial (Bruno, Marcello, Cruz, Gabriel(cyrillo). Exemplo:
   - [X] [B] Adicionar Exemplo
   
 - Estruturação das packages:
    - Src
      - Interface
      - Input/Output
      - Test cases
      - Functions
 

 - Funcoes que editam a imagem
 
    - Retornam uma imagem nova
    
    - Nao mudam o que foi passado
    
    - Sugerem um shortcut

# TODO
  - Pré-projeto
     - [X] Escrever um README decente
     - [X] Escolher tema
     - [X] Desenvolver projeto básico de aplicação: https://www.overleaf.com/9610901vpynnzdrykcs
     - [X] Enviar proposta de projeto

  - TASK TO BE DONE

    - Interface
         - [] Escolher a interface
         - [] Top menu
         - [] Left menu
         - [] Right menu
         - [] Mostrar a imagem na janela, selecionando com window maneger

    - Usabilidade
         - [] Salvar a imagem com novo arquivo or not
         - [] Clicar e pegar as coordenadas do pixel
         - [] Click dos usuarios
    - Outros
         - [] Projetar o javadoc (ASAP)

# Functions
  - Crop
    - Seleção retangular
    - Seleção direta (com o mouse)
    
  - Rotação
  
  - Distorção e Redimensionalização 
  
  - Filters
    - Mundanças de canais HSL e RGB (dá para montar filtros tipo instagram com isso)
    - Realce de Imagens 
    - Convolução de matrizes: Blur, sharp
    
  - Layers
    - Prioridade de cada layer e sobreposição desses
  
  - Desenho
    - Brush (desenhar com o mouse)
    - Bucket
    - Color selector (menu e por pixel na imagem)
 
  - Extra
    - Seleção por isomorfismo (mesma cor, textura, etc)
    - Remoção de ruído
    - Formas geométricas e Adesivos padrão
    - Abrir uma imagem remotamente
    - Shortcuts
    
