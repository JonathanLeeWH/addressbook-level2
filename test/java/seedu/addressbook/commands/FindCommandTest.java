package seedu.addressbook.commands;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.util.TypicalPersons;

public class FindCommandTest {

    private final AddressBook addressBook = new TypicalPersons().getTypicalAddressBook();
    private final TypicalPersons td = new TypicalPersons();

    @Test
    public void execute() throws IllegalValueException {
        //same word, same case: matched
        assertFindCommandBehavior(new String[]{"Amy"}, Arrays.asList(td.getAmy()));

        //same word, different case: matched
        assertFindCommandBehavior(new String[]{"aMy"}, Arrays.asList(td.getAmy()));

        //partial word: not matched
        assertFindCommandBehavior(new String[]{"my"}, Collections.emptyList());

        //multiple words: matched
        assertFindCommandBehavior(new String[]{"Amy", "Bill", "Candy", "Destiny"},
                Arrays.asList(td.getAmy(), td.getBill(), td.getCandy()));

        //repeated keywords: matched
        assertFindCommandBehavior(new String[]{"Amy", "Amy"}, Arrays.asList(td.getAmy()));

        //Keyword matching a word in address: not matched
        assertFindCommandBehavior(new String[]{"Clementi"}, Collections.emptyList());
    }

    /**
     * Executes the find command for the given keywords and verifies
     * the result matches the persons in the expectedPersonList exactly.
     */
    private void assertFindCommandBehavior(String[] keywords, List<ReadOnlyPerson> expectedPersonList) {
        FindCommand command = createFindCommand(keywords);
        CommandResult result = command.execute();

        assertEquals(Command.getMessageForPersonListShownSummary(expectedPersonList), result.feedbackToUser);
    }

    /**
     * Returns the find command with the input keywords.
     * @param keywords The input keywords.
     * @return The find command with the input keywords.
     */
    private FindCommand createFindCommand(String[] keywords) {
        for (int i = 0; i < keywords.length; i++) {
            keywords[i] = keywords[i].toUpperCase();
        }
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        FindCommand command = new FindCommand(keywordSet);
        command.setData(addressBook, Collections.emptyList());
        return command;
    }

}
