package com.example.kohiapp.Gacha;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Gacha {

    private List<Reward> rewards;
    private Random random;
    private FirebaseFirestore db;
    private CollectionReference userRewardsRef,rewardsRef;
    private String uid, rewardGift;
    private ArrayList<String> userRewards;

    public Gacha(String uid) {
        rewards = new ArrayList<>();
        random = new Random();
        db = FirebaseFirestore.getInstance();
        rewardsRef = db.collection("rewards");
        userRewardsRef = db.collection("users_rewards").document(uid).collection("rewards");
        this.uid = uid;
        userRewards = new ArrayList<>();
        rewardPool(); // Initialize the reward pool
        loadUserRewards(); // Load the user's rewards from Firestore
    }

    // Initialize the reward pool with some default rewards
    private void rewardPool() {
        rewards.add(new Reward("ds1"));
        rewards.add(new Reward("ds2"));
        rewards.add(new Reward("ds3"));
        rewards.add(new Reward("ds4"));
        rewards.add(new Reward("ds5"));
        rewards.add(new Reward("ds6"));
        rewards.add(new Reward("ds7"));
        rewards.add(new Reward("ds8"));
        rewards.add(new Reward("ds9"));
        rewards.add(new Reward("ds10"));
        rewards.add(new Reward("ds11"));
        rewards.add(new Reward("ds12"));
        rewards.add(new Reward("ds13"));
        rewards.add(new Reward("ds14"));
        rewards.add(new Reward("ds15"));
        rewards.add(new Reward("ds16"));
        rewards.add(new Reward("ds17"));
        rewards.add(new Reward("ds18"));
        rewards.add(new Reward("ds19"));
        rewards.add(new Reward("ds20"));
        rewards.add(new Reward("ds21"));
        rewards.add(new Reward("ds22"));
        rewards.add(new Reward("ds23"));
        rewards.add(new Reward("ds24"));
        rewards.add(new Reward("ds25"));
        rewards.add(new Reward("ds26"));
        rewards.add(new Reward("ds27"));
        rewards.add(new Reward("ds28"));
        rewards.add(new Reward("ds29"));
        rewards.add(new Reward("ds30"));
        rewards.add(new Reward("ds31"));
        rewards.add(new Reward("ds32"));
        rewards.add(new Reward("ds33"));
        rewards.add(new Reward("ds34"));
        rewards.add(new Reward("ds35"));
        rewards.add(new Reward("ds36"));
        rewards.add(new Reward("dg1"));
        rewards.add(new Reward("dg2"));
        rewards.add(new Reward("dg3"));
        rewards.add(new Reward("dg4"));
        rewards.add(new Reward("dg5"));
        rewards.add(new Reward("dg6"));
        rewards.add(new Reward("dg7"));
        rewards.add(new Reward("dg8"));


    }

    // Play the gacha game
    public void play() {
        if (rewards.size() > 0) {
            int index = random.nextInt(rewards.size()); // Select a random reward
            Reward reward = rewards.get(index);
            String rewardName = reward.getName();
            userRewards.add(rewardName); // Add the reward to the user's list of obtained rewards
            saveUserReward(rewardName); // Save the reward to Firestore
            rewards.remove(index); // Remove the reward from the reward pool
            rewardGift = rewardName;
        } else {rewardGift = "No More rewards";
        }
        System.out.println(rewardGift);
    }

    // Save the user's reward to Firestore
    private void saveUserReward(String rewardName) {
        // Create a map with the reward data
        Map<String, Object> rewardData = new HashMap<>();
        // Add the reward name to the map with the key "reward"
        rewardData.put("reward", rewardName);

        // Save the reward to Firestore
        db.collection("users_rewards").document(uid).collection("rewards").add(rewardData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
//                        System.out.println("Reward " + rewardName + " saved to Firestore.");
                    }
                });
    }

    // Load the user's rewards from Firestore
    private void loadUserRewards() {
        // Get the user's rewards from Firestore
        userRewardsRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // Loop through the user's rewards and add them to the list of obtained rewards
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    String rewardName = documentSnapshot.getString("reward");
                    userRewards.add(rewardName);

                    // Remove the reward from the pool if the user has already obtained it
                    rewards.removeIf(reward -> reward.getName().equals(rewardName));
                }
            }
        });
    }

    public String getRewardGift() {
        return rewardGift;
    }
}


