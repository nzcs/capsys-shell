package hu.capsys.shell.recon;

import hu.capsys.gateway.recon.api.model.ErrorItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.stream.Collectors;

import static hu.capsys.shell.Util.print;
import static java.util.Objects.requireNonNull;

@ShellComponent
@RequiredArgsConstructor
public class ReconCommands {

    final ReconService service;


    @ShellMethod("List Error Items")
    public void listErrorItems() {
        ResponseEntity<List<ErrorItemDto>> result = service.listErrorItems();
        List<ErrorItemDto> list = requireNonNull(result.getBody());
        print("List Error Items",
                result.getStatusCode(),
                list.stream().collect(Collectors.toMap(ErrorItemDto::getReference, ErrorItemDto::getStatus))
        );
    }
}
