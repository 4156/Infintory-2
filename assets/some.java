        Events.on(EventType.ClientLoadEvent.class, (e) -> {
            ResearchFragment fragment = new ResearchFragment();
            fragment.build(Vars.ui.hudGroup);
            Events.on(EventType.WorldLoadEvent.class, (et) -> {
                fragment.table.visible = true;
            });
        });