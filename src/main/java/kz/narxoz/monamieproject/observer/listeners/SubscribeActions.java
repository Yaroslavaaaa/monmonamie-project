package kz.narxoz.monamieproject.observer.listeners;

public class SubscribeActions implements EventListener{
    @Override
    public void subscribe(String email) {
//        User user = userRepository.findByEmail(email);
//        if (user != null){
//            User.setSubscribed(true);
//        }else{
//            сначала зарегестрируйтесь;
//        }
    }

    @Override
    public void unsubscribe(String email) {
//        if (user != null){
//            User.setSubscribed(false);
//        }else{
//            сначала зарегестрируйтесь;
//        }
    }

    @Override
    public void news(String news) {
        System.out.println("");
    }
}
