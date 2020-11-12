package com.rubycon.rubyconteam2.global.factory;

import com.rubycon.rubyconteam2.domain.party.domain.Party;
import com.rubycon.rubyconteam2.domain.party_join.domain.PartyJoin;
import com.rubycon.rubyconteam2.domain.party_join.domain.Role;
import com.rubycon.rubyconteam2.domain.user.domain.User;

public class TestPartyJoinFactory {

    public static PartyJoin createPartyJoin(User user, Party party, Role role){
        return PartyJoin.of(user, party, role);
    }
}
