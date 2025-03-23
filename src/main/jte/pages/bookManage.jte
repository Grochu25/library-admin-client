@import com.grochu.libraryadminclient.Domain.Borrow
@import com.grochu.libraryadminclient.Domain.Copy
@import com.grochu.libraryadminclient.Domain.Book
@import java.util.Map
@param Book book
@param Map<Copy, Boolean> copies
@param Map<Copy, Borrow> borrowInfo

@template.layout.main(content = @`
<a href="/books">Wróć do listy</a>
<br/>
<div><h1 style="float:left;">Tytuł: ${book.getTitle()}</h1><a href="/books/add?bookId=${book.getId()}" style="float:left;">edytuj</a></div>
<h2 style="clear:both;">Autor: <a href="/authors/${book.getAuthor().getId()}">${book.getAuthor().getName()}</a></h2>
<h2>Rok wydania: ${book.getPublishYear()}</h2>

<form method="post" action="/books/${book.getId()}/add">
    <button type="submit">Automatycznie dodaj nową kopię</button>
</form>
lub
<br/>
<form method="post" action="/books/${book.getId()}/add">
    <input type="number" name="copyId"/>
    <button type="submit">Dodaj nową kopię z numerem</button>
</form>
<br/>

<table style="border: solid black 2px;">
    <thead>
        <tr>
            <th>Numer kopi </th>
            <th>Dostępna </th>
            <th>Wypożyczona przez</th>
            <th></th>
        </tr>
    </thead>
    <tbody>
@for(Copy copy : copies.keySet())
    <tr>
        <td>${copy.getId()}</td>
            @if(copies.get(copy))
                <td>Tak</td>
                <td><a href="/customers?borrowMode=true&copyId=${copy.getId()}&bookId=${copy.getBook().getId()}">Wypożycz</a></td>
                <td><a href="/books/${book.getId()}/copyDelete?copyId=${copy.getId()}">Usuń kopię ze zbiorów</a></td>
            @elseif(!copy.getDestroyed())
                <td>Nie</td>
                <td><a href="/customers/${borrowInfo.get(copy).getUser().getId()}">
                        ${borrowInfo.get(copy).getUser().getSurname()} ${borrowInfo.get(copy).getUser().getName()}</a></td>
                <td><a href="/books/${book.getId()}/copy/${copy.getId()}/return">Zwrot</a></td>
            @else
                <td>Nie</td>
                <td colspan="2">Egzemplarz zniszczony</td>
            @endif

    </tr>
@endfor
    </tbody>
</table>
`)