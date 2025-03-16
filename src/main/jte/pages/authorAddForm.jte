
@template.layout.main(content = @`
<a href="/authors">Wróć do listy</a>
<h1>Dodaj nowego autora</h1>

<form method="post" action="/authors/add">
    <label for="name">Imie i nazwisko:</label>
    <input id="name" name="name"/>
    <br/>

    <label for="birthYear">Rok urodzenia autora:</label>
    <input name="birthYear" id="birthYear" type="number"/>
    (Opcjonalnie)
    <br/>

    <label for="description">Opis:</label>
    <textarea id="description" name="description"></textarea>
    (Opcjonalnie)
    <br/>

    <button type="submit">Dodaj</button>
</form>
`)