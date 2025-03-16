@template.layout.main(content = @`
<a href="/books">Wróć do listy</a>
<h1>Dodaj nową książke</h1>

<form method="post" action="/books/add/authorAndBook">
    <label for="title">Tytuł:</label>
    <input id="title" name="title"/>
    <br/>

    <label for="author">Imię i nazwisko autora:</label>
    <input name="author" id="author"/> (dalej będą pozostałe dane do uzupełnienia, na razie nie dodane)
    <br/>

    <label for="publishYear">Rok publikacji:</label>
    <input id="publishYear" name="publishYear" type="number"/>
    <br/>

    <button type="submit">Dodaj</button>
</form>
`)