package org.oppia.android.testing.logging

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.oppia.android.app.model.AppLanguageSelection
import org.oppia.android.app.model.AudioTranslationLanguageSelection
import org.oppia.android.app.model.EventLog
import org.oppia.android.app.model.EventLog.ExplorationContext
import org.oppia.android.app.model.EventLog.TopicContext
import org.oppia.android.app.model.OppiaLanguage
import org.oppia.android.app.model.ProfileId
import org.oppia.android.app.model.WrittenTranslationLanguageSelection

@RunWith(JUnit4::class)
class EventLogSubjectTest {
  @Test
  fun testHasTimeStamp_withTimeStamp_matchesTimeStamp() {
    val eventLog = EventLog.newBuilder()
      .setTimestamp(123456789)
      .build()

    EventLogSubject.assertThat(eventLog)
      .hasTimestampThat()
      .isEqualTo(123456789)
  }

  @Test(expected = AssertionError::class)
  fun testHasTimeStamp_withDifferentTimeStamp_fails() {
    val eventLog = EventLog.newBuilder()
      .setTimestamp(123456789)
      .build()

    EventLogSubject.assertThat(eventLog)
      .hasTimestampThat()
      .isEqualTo(987654321)
  }

  @Test
  fun testIsEssentialPriority_withEssentialPriority_matchesEssentialPriority() {
    val eventLog = EventLog.newBuilder()
      .setPriority(EventLog.Priority.ESSENTIAL)
      .build()

    EventLogSubject.assertThat(eventLog)
      .isEssentialPriority()
  }

  @Test(expected = AssertionError::class)
  fun testIsEssentialPriority_withDifferentPriority_fails() {
    val eventLog = EventLog.newBuilder()
      .setPriority(EventLog.Priority.OPTIONAL)
      .build()

    EventLogSubject.assertThat(eventLog)
      .isEssentialPriority()
  }

  @Test
  fun testIsOptionalPriority_withOptionalPriority_matchesOptionalPriority() {
    val eventLog = EventLog.newBuilder()
      .setPriority(EventLog.Priority.OPTIONAL)
      .build()

    EventLogSubject.assertThat(eventLog)
      .isOptionalPriority()
  }

  @Test(expected = AssertionError::class)
  fun testIsOptionalPriority_withDifferentPriority_fails() {
    val eventLog = EventLog.newBuilder()
      .setPriority(EventLog.Priority.ESSENTIAL)
      .build()

    EventLogSubject.assertThat(eventLog)
      .isOptionalPriority()
  }

  @Test
  fun testHasNoProfileId_withNoProfileId() {
    val eventLog = EventLog.newBuilder()
      .build()

    EventLogSubject.assertThat(eventLog)
      .hasNoProfileId()
  }

  @Test(expected = AssertionError::class)
  fun testHasNoProfileId_withProfileId_fails() {
    val profileId = ProfileId.newBuilder()
      .setInternalId(1)
      .build()
    val eventLog = EventLog.newBuilder()
      .setProfileId(profileId)
      .build()

    EventLogSubject.assertThat(eventLog)
      .hasNoProfileId()
  }

  @Test
  fun testHasProfileId_withProfileId_matchesProfileId() {
    val profileId = ProfileId.newBuilder()
      .setInternalId(1)
      .build()
    val eventLog = EventLog.newBuilder()
      .setProfileId(profileId)
      .build()

    EventLogSubject.assertThat(eventLog)
      .hasProfileIdThat()
      .isEqualTo(profileId)
  }

  @Test(expected = AssertionError::class)
  fun testHasProfileId_withDifferentProfileId_fails() {
    val profileId = ProfileId.newBuilder()
      .setInternalId(1)
      .build()
    val eventLog = EventLog.newBuilder()
      .setProfileId(profileId)
      .build()
    val differentProfileId = ProfileId.newBuilder()
      .setInternalId(2)
      .build()

    EventLogSubject.assertThat(eventLog)
      .hasProfileIdThat()
      .isEqualTo(differentProfileId)
  }

  @Test
  fun testHasAppLanguageSelectionThat_withAppLanguageSelection_matchesAppLanguageSelection() {
    val appLanguageSelection = AppLanguageSelection.newBuilder()
      .setSelectedLanguage(OppiaLanguage.ENGLISH)
      .build()
    val eventLog = EventLog.newBuilder()
      .setAppLanguageSelection(appLanguageSelection)
      .build()

    EventLogSubject.assertThat(eventLog)
      .hasAppLanguageSelectionThat()
      .isEqualTo(appLanguageSelection)
  }

  @Test(expected = AssertionError::class)
  fun testHasAppLanguageSelectionThat_withDifferentAppLanguageSelection_fails() {
    val appLanguageSelection = AppLanguageSelection.newBuilder()
      .setSelectedLanguage(OppiaLanguage.ENGLISH)
      .build()
    val eventLog = EventLog.newBuilder()
      .setAppLanguageSelection(appLanguageSelection)
      .build()
    val differentAppLanguageSelection = AppLanguageSelection.newBuilder()
      .setSelectedLanguage(OppiaLanguage.ARABIC)
      .build()

    EventLogSubject.assertThat(eventLog)
      .hasAppLanguageSelectionThat()
      .isEqualTo(differentAppLanguageSelection)
  }

  @Test
  fun testHasWrittenTranslationLanguageSelectionThat_matchcesWrittenTranslationLanguageSelection() {
    val writtenTranslationLanguageSelection = WrittenTranslationLanguageSelection.newBuilder()
      .setSelectedLanguage(OppiaLanguage.ENGLISH)
      .build()
    val eventLog = EventLog.newBuilder()
      .setWrittenTranslationLanguageSelection(writtenTranslationLanguageSelection)
      .build()

    EventLogSubject.assertThat(eventLog)
      .hasWrittenTranslationLanguageSelectionThat()
      .isEqualTo(writtenTranslationLanguageSelection)
  }

  @Test(expected = AssertionError::class)
  fun testHasWrittenTranslationLanguageSelectionThat_withDifferentLanguageSelection_fails() {
    val writtenLanguageSelection = WrittenTranslationLanguageSelection.newBuilder()
      .setSelectedLanguage(OppiaLanguage.ENGLISH)
      .build()
    val eventLog = EventLog.newBuilder()
      .setWrittenTranslationLanguageSelection(writtenLanguageSelection)
      .build()
    val differentLanguageSelection = WrittenTranslationLanguageSelection.newBuilder()
      .setSelectedLanguage(OppiaLanguage.ARABIC)
      .build()

    EventLogSubject.assertThat(eventLog)
      .hasWrittenTranslationLanguageSelectionThat()
      .isEqualTo(differentLanguageSelection)
  }

  @Test
  fun testHasAudioTranslationLanguageSelectionThat_withMatchingSelection_passes() {
    val audioTranslationLanguageSelection = AudioTranslationLanguageSelection.newBuilder()
      .setSelectedLanguage(OppiaLanguage.ENGLISH)
      .build()
    val eventLog = EventLog.newBuilder()
      .setAudioTranslationLanguageSelection(audioTranslationLanguageSelection)
      .build()

    EventLogSubject.assertThat(eventLog)
      .hasAudioTranslationLanguageSelectionThat()
      .isEqualTo(audioTranslationLanguageSelection)
  }

  @Test(expected = AssertionError::class)
  fun testHasAudioTranslationLanguageSelectionThat_withDifferentSelection_fails() {
    val audioTranslationLanguageSelection = AudioTranslationLanguageSelection.newBuilder()
      .setSelectedLanguage(OppiaLanguage.ENGLISH)
      .build()
    val eventLog = EventLog.newBuilder()
      .setAudioTranslationLanguageSelection(audioTranslationLanguageSelection)
      .build()
    val differentSelection = AudioTranslationLanguageSelection.newBuilder()
      .setSelectedLanguage(OppiaLanguage.ARABIC)
      .build()

    EventLogSubject.assertThat(eventLog)
      .hasAudioTranslationLanguageSelectionThat()
      .isEqualTo(differentSelection)
  }

  @Test
  fun testHasOpenExplorationActivityContext_withMatchingContext_passes() {
    val eventLog = EventLog.newBuilder()
      .setContext(
        EventLog.Context.newBuilder()
          .setOpenExplorationActivity(ExplorationContext.newBuilder())
      )
      .build()

    EventLogSubject.assertThat(eventLog)
      .hasOpenExplorationActivityContext()
  }

  @Test(expected = AssertionError::class)
  fun testHasOpenExplorationActivityContext_withDifferentContext_fails() {
    val eventLog = EventLog.newBuilder()
      .build()

    EventLogSubject.assertThat(eventLog)
      .hasOpenExplorationActivityContext()
  }

  @Test
  fun testHasOpenInfoTabContext_withMatchingContext_passes() {
    val eventLog = EventLog.newBuilder()
      .setContext(
        EventLog.Context.newBuilder()
          .setOpenInfoTab(TopicContext.newBuilder())
      )
      .build()

    EventLogSubject.assertThat(eventLog)
      .hasOpenInfoTabContext()
  }

  @Test
  fun testHasOpenLessonsTabContext_withMatchingContext_passes() {
    val eventLog = EventLog.newBuilder()
      .setContext(
        EventLog.Context.newBuilder()
          .setOpenLessonsTab(TopicContext.newBuilder())
      )
      .build()

    EventLogSubject.assertThat(eventLog)
      .hasOpenLessonsTabContext()
  }

  @Test
  fun testHasOpenPracticeTabContext_withMatchingContext_passes() {
    val eventLog = EventLog.newBuilder()
      .setContext(
        EventLog.Context.newBuilder()
          .setOpenPracticeTab(TopicContext.newBuilder())
      )
      .build()

    EventLogSubject.assertThat(eventLog)
      .hasOpenPracticeTabContext()
  }

  @Test
  fun testHasOpenRevisionTabContext_withMatchingContext_passes() {
    val eventLog = EventLog.newBuilder()
      .setContext(
        EventLog.Context.newBuilder()
          .setOpenRevisionTab(TopicContext.newBuilder())
      )
      .build()

    EventLogSubject.assertThat(eventLog)
      .hasOpenRevisionTabContext()
  }
}
