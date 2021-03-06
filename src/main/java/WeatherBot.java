import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.MessageContext;
import parcers.OpenWeatherMapJsonParser;
import parcers.WeatherParser;

import static org.telegram.abilitybots.api.objects.Flag.TEXT;
import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

public class WeatherBot extends AbilityBot {
    private static final String BOT_TOKEN = "5264726797:AAEEm1B5-voC-buFeLg7KQzuL5ALuJDQXH4";
    private static final String BOT_NAME = "weatherallbot";
    private WeatherParser weatherParser = new OpenWeatherMapJsonParser();


    public WeatherBot() {
        super(BOT_TOKEN, BOT_NAME);
    }

    //Replace "-1" with your Telegram user id
    @Override
    public long creatorId() {
        return 676494085;
    }

    public Ability startCommand() {
        return Ability
                .builder()
                .name("start")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send("Hello! Enter the city in chat and get 5 days forecast! " +
                        "For example: \"New York\" or \"Istanbul\"", ctx.chatId()))
                .build();
    }

    public Ability sendWeather() {
        return Ability.builder()
                .name(DEFAULT)
                .flag(TEXT)
                .privacy(PUBLIC)
                .locality(ALL)
                .input(0)
                .action((MessageContext ctx) -> {
                    if (ctx.firstArg().equals(ctx.secondArg())) {
                        silent.send(weatherParser.getReadyForecast(ctx.firstArg()), ctx.chatId());
                    } else {
                        silent.send(weatherParser.getReadyForecast(String.format("%s %s", ctx.firstArg(), ctx.secondArg())), ctx.chatId());
                    }
                })
                .build();
    }
}
