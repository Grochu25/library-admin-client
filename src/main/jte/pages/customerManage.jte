@import com.grochu.libraryadminclient.Domain.Borrow
@import com.grochu.libraryadminclient.Domain.Copy
@import com.grochu.libraryadminclient.Domain.User
@import java.util.List
@param User user
@param List<Borrow> borrows

@template.layout.main(content = @`
<a href="/customers">Wróć do czytelników</a>
<h1>Imię i Nazwisko: ${user.getName()} ${user.getSurname()}</h1>
<h2>Email: ${user.getEmail()}</h2>
<h2>Telefon: ${user.getPhoneNumber()}</h2>
<h2>Adres: ${user.getAddressStreet()}<br/>${user.getAddressZip()} ${user.getAddressCity()}<br/>${user.getAddressState()}</h2>

Historia czytelnika:
<table style="border: solid black 2px;">
    <thead>
    <tr>
        <th>Tytuł książki </th>
        <th>Numer kopii </th>
        <th>Data wypożyczenia </th>
        <th>Data zwrotu</th>
    </tr>
    </thead>
    <tbody>
    @for(Borrow borrow : borrows)
        <tr>
            <td>${borrow.getCopy().getBook().getTitle()}</td>
            <td>${borrow.getCopy().getId()}</td>
            <td>${borrow.getSince().toString()}</td>
            @if(borrow.getUntil() == null)
                <td><a href="/books/${borrow.getCopy().getBook().getId()}/copy/${borrow.getCopy().getId()}/return?userId=${borrow.getUser().getId()}">Zwróć</a></td>
            @else
                <td>${borrow.getUntil().toString()}</td>
            @endif
        </tr>
    @endfor
    </tbody>
</table>
`)