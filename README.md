# Atividade: SGBD para produtos

Um atividade da disciplina de LP 2 que tem como objetivo desenvolver um gerenciador de produtos com dados persistentes. 

# Justificativas
Aqui deixarei as minhas justificativas de algumas decisões que tomei no desenvolvimento:

- Por que usar `Thread` para salvar os dados em segundo plano?
    - De fato, como se trata de um processo simples eu poderia só deixar para salvar os dados persistentes ao encerrar a aplicação (se o foco é performance) ou a cada alteração neles (se o foco é não perder os dados). Só que eu estou estutando `Threads`, e com uma `Thread` a perda de performance é relativamente pequena e consigo manter os dados atualizados sempre que necessário também (o bom dos dois mundos);
- Por que separa a interface gráfica em outra `lib`?
    - Não era a intenção inicial, mas eu achei que a parte gráfica ficou tão bem feita e generalizada que eu quis separar em uma biblioteca própria, que chamei de [`jpretty`](https://github.com/l-marcel/jpretty). Inicialmente era um submódulo, mas mudei de ideia.