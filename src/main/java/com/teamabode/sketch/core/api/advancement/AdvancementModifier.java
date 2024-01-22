package com.teamabode.sketch.core.api.advancement;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdvancementModifier {
    private final ResourceLocation id;
    private final ResourceLocation advancement;
    private final Map<String, Criterion> criteria;
    private final List<RequirementModifier> requirementModifiers;

    public AdvancementModifier(ResourceLocation id, ResourceLocation advancement, Map<String, Criterion> criteria, List<RequirementModifier> requirementModifiers) {
        this.id = id;
        this.advancement = advancement;
        this.criteria = criteria;
        this.requirementModifiers = requirementModifiers;
    }

    public static class Builder {
        private final ResourceLocation advancement;
        private final Map<String, Criterion> criteria;
        private final ArrayList<RequirementModifier> requirementModifiers;

        public Builder(ResourceLocation advancement, Map<String, Criterion> criteria, ArrayList<RequirementModifier> requirementModifiers) {
            this.advancement = advancement;
            this.criteria = Maps.newLinkedHashMap();
            this.requirementModifiers = Lists.newArrayList();
        }

        public AdvancementModifier.Builder addCriterion(String key, CriterionTriggerInstance criterion) {
            return this.addCriterion(key, new Criterion(criterion));
        }

        public AdvancementModifier.Builder addCriterion(String key, Criterion criterion) {
            if (this.criteria.containsKey(key)) {
                throw new IllegalArgumentException("Duplicate criterion " + key);
            } else {
                this.criteria.put(key, criterion);
                return this;
            }
        }

        public AdvancementModifier.Builder addRequirementModifier(RequirementModifier requirementModifier) {
            this.requirementModifiers.add(requirementModifier);
            return this;
        }

        public AdvancementModifier build(ResourceLocation id) {
            return new AdvancementModifier(id, this.advancement, this.criteria, this.requirementModifiers);
        }

        public static AdvancementModifier.Builder fromJSON(JsonObject root, DeserializationContext context) {
            ResourceLocation advancement = root.has("advancement") ? new ResourceLocation(GsonHelper.getAsString(root, "advancement")) : null;
            Map<String, Criterion> criteria = Criterion.criteriaFromJson(GsonHelper.getAsJsonObject(root, "criteria"), context);
            ArrayList<RequirementModifier> requirementModifiers = Lists.newArrayList();

            if (criteria.isEmpty()) {
                throw new JsonSyntaxException("Advancement modifier criteria cannot be empty");
            }
            return new Builder(advancement, criteria, requirementModifiers);
        }
    }
}
