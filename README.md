## Configuração

É necessário adicionar um arquivo **secrets.properties** no diretório do **executável da aplicação**, substituindo
o que está entre **<>** por sua configuração própria:

```
#### ------------------------ ARQUIVO DE SEGREDOS ------------------------ ####
#### - NÃO deve ser versionado/adicionado ao git, nem exposto publicamente ####
#### --------------------------------------------------------------------- ####

# Banco de dados
spring.datasource.url = jdbc:postgresql://<IP>:<Porta>/<NomeDoBD>
spring.datasource.username = <UsuárioDoBD>
spring.datasource.password = <SenhaDoBD>

# Caminho para gravar/ler arquivos submetidos pelos usuários (uploads)
armazenamento.caminho=./<Caminho>/

# Adiciona a pasta de uploads aos recursos estáticos (sobrescrita da configuração padrão)
# https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#boot-features-spring-mvc-static-content
spring.resources.static-locations=classpath:/resources/,classpath:/static/,classpath:/public/,file:${armazenamento.caminho}
```
