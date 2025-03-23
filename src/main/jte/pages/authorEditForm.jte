@import com.grochu.libraryadminclient.Domain.Author
@param Author author = null

@template.layout.main(content = @`
<a href="/authors/${author.getId()}">Wróć do autora</a>
<h1>Edytuj autora</h1>

<form method="post" action="/authors/add">
    <input name="id" type="hidden" readonly="true" value="${author.getId()}"/>
    <label for="name">Imie i nazwisko:</label>
    <input id="name" name="name" value="${author.getName()}"/>
    <br/>

    <label for="birthYear">Rok urodzenia autora:</label>
    <input name="birthYear" id="birthYear" type="number" value="${author.getBirthYear()}"/>
    (Opcjonalnie)
    <br/>

    <label for="description">Opis:</label>
    <textarea id="description" name="description">${author.getDescription()}</textarea>
    (Opcjonalnie)
    <br/>

    <button type="submit">Dodaj</button>
</form>
`)