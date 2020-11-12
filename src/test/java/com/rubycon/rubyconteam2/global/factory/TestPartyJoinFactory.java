package com.rubycon.rubyconteam2.global.factory;

import com.rubycon.rubyconteam2.domain.party.domain.Party;
import com.rubycon.rubyconteam2.domain.party.domain.ServiceType;
import com.rubycon.rubyconteam2.domain.party_join.domain.PartyJoin;
import com.rubycon.rubyconteam2.domain.party_join.domain.Role;
import com.rubycon.rubyconteam2.domain.user.domain.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestPartyJoinFactory {

    public static PartyJoin createPartyJoin(User user, Party party, Role role){
        return PartyJoin.of(user, party, role);
    }

    public static List<PartyJoin> findAllPartyJoins(){
        List<User> users = TestUserFactory.getTestUsers();
        Party party = TestPartyFactory.createParty(1L, ServiceType.APPLE_MUSIC);

        return new ArrayList<>(
                Arrays.asList(
                        createPartyJoin(users.get(0), party, Role.LEADER),
                        createPartyJoin(users.get(1), party, Role.MEMBER),
                        createPartyJoin(users.get(2), party, Role.MEMBER)
                )
        );
    }
}
