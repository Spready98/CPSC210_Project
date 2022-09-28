# Drafting Tool for League

## A strategic pick/ban simulator for League of Legends

League of Legends is a popular multiplayer online battleground arena (moba) game where two teams of 5 battle and each player pilots a *unique* "champion". It has over **100 million monthly active players** worldwide and roughly **24 million hours** of league content are watched each week Twitch. Before a game of league starts, players take turns banning and picking champions in the *drafting* phase. With over 155 champions (and counting), each with their own unique strengths and weaknesses, drafting a better team than the opponents can significantly increase the chances of a victory.

My project will simulate the drafting phase of a League of Legends game and let players see relevant information about a champion, such as their characteristics, to aid  in their decision-making. A draft begins with each team selecting 3 champions to ban, then each team picks 3 champions in order before banning 2 more champions each. Lastly, each team picks their last 2 champions resulting in 10 picks and 10 bans throughout the entire drafting phase. Any aspiring coach, analyst, or competitive player can use this application as a practice tool against each other. Competitive gaming or *esports* is one of my passions and the broad criteria was the perfect opportunity to turn a school project into a passion project.


## User Stories

- As a user, I want to be able to add a champion(s) to my team
- As a user, I want to be able to add a champion to the banned list
- As a user, I want to be able to view the stats of my team
- As a user, I want to be able to view the draft (in real time?)
- As a user, I want to be able to save the state of a draft
- As a user, I want to be able to load a saved draft at the start

## Phase 4: Task 2
Wed Nov 24 01:38:43 PST 2021
Added Champion: ashe to team or ban list


Wed Nov 24 01:38:43 PST 2021
Added Champion: leona to team or ban list


Wed Nov 24 01:38:43 PST 2021
Added Champion: lucian to team or ban list


Wed Nov 24 01:38:43 PST 2021
Added Champion: yasuo to team or ban list


Wed Nov 24 01:38:43 PST 2021
Added Champion: annie to team or ban list


Wed Nov 24 01:38:43 PST 2021
Added Champion: brand to team or ban list


Wed Nov 24 01:38:43 PST 2021
Added Champion: yone to team or ban list


Wed Nov 24 01:38:43 PST 2021
Added Champion: ryze to team or ban list


Wed Nov 24 01:38:43 PST 2021
Added Champion: rell to team or ban list


Wed Nov 24 01:38:43 PST 2021
Added Champion: bard to team or ban list


Wed Nov 24 01:38:43 PST 2021
Added Champion: jhin to team or ban list


Wed Nov 24 01:38:43 PST 2021
Added Champion: braum to team or ban list


Wed Nov 24 01:38:43 PST 2021
Added Champion: aphelios to team or ban list


Wed Nov 24 01:38:43 PST 2021
Added Champion: gragas to team or ban list


Wed Nov 24 01:38:43 PST 2021
Added Champion: graves to team or ban list


Wed Nov 24 01:38:43 PST 2021
Added Champion: akali to team or ban list


Wed Nov 24 01:38:43 PST 2021
Added Champion: ezreal to team or ban list


Wed Nov 24 01:38:43 PST 2021
Added Champion: singed to team or ban list


Wed Nov 24 01:38:43 PST 2021
Added Champion: yuumi to team or ban list


Wed Nov 24 01:38:43 PST 2021
Added Champion: ziggs to team or ban list


Wed Nov 24 01:38:55 PST 2021
Removed Champion: bard from Champion pool


Wed Nov 24 01:38:57 PST 2021
Removed Champion: gragas from Champion pool


Wed Nov 24 01:39:01 PST 2021
Added Champion: ashe to team or ban list


Wed Nov 24 01:39:01 PST 2021
Removed Champion: ashe from Champion pool


Wed Nov 24 01:39:04 PST 2021
Added Champion: lucian to team or ban list


Wed Nov 24 01:39:04 PST 2021
Removed Champion: lucian from Champion pool


Wed Nov 24 01:39:08 PST 2021
Added Champion: rell to team or ban list


Wed Nov 24 01:39:08 PST 2021
Removed Champion: rell from Champion pool


Wed Nov 24 01:39:10 PST 2021
Added Champion: ezreal to team or ban list


Wed Nov 24 01:39:10 PST 2021
Removed Champion: ezreal from Champion pool


Wed Nov 24 01:39:13 PST 2021
Added Champion: graves to team or ban list


Wed Nov 24 01:39:13 PST 2021
Removed Champion: graves from Champion pool


Wed Nov 24 01:39:19 PST 2021
Added Champion: annie to team or ban list


Wed Nov 24 01:39:19 PST 2021
Removed Champion: annie from Champion pool

## Phase 4: Task 3
    - I have a lot of methods in DraftApp that are really similar so I would use
      abstraction to refactor these "clone" methods (ex. RedDraft() & BlueDraft())
    - I would like to refactor Role and let each role have its own class that extends
      Role. That way, each role would have its own unique traits for a Champion
    - In the class ChampionList, there are two methods addChampion and 
      addAvailableChampion that could be refactored into one method with slight changes.