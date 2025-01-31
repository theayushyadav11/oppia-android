package org.oppia.android.testing.logging

import org.junit.Assert.assertThrows
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

/** Tests for [EventLogSubject]. */
@RunWith(JUnit4::class)
class EventLogSubjectTest {
  @Test
  fun testEventLogSubject_matchesCorrectTimeStamp() {
    val eventLog = EventLog.newBuilder()
      .setTimestamp(123456789)
      .build()

    EventLogSubject.assertThat(eventLog)
      .hasTimestampThat()
      .isEqualTo(123456789)
  }

  @Test
  fun testEventLogSubject_failsOnUnmatchingTimestamp() {
    val eventLog = EventLog.newBuilder()
      .setTimestamp(123456789)
      .build()

    assertThrows(AssertionError::class.java) {
      EventLogSubject.assertThat(eventLog)
        .hasTimestampThat()
        .isEqualTo(987654321)
    }
  }

  @Test
  fun testEventLogSubject_matchesPriorityEssential() {
    val eventLog = EventLog.newBuilder()
      .setPriority(EventLog.Priority.ESSENTIAL)
      .build()

    EventLogSubject.assertThat(eventLog)
      .isEssentialPriority()
  }

  @Test
  fun testEventLogSubject_matchEssentialPriorityWithDifferentPriority_fails() {
    val eventLog = EventLog.newBuilder()
      .setPriority(EventLog.Priority.OPTIONAL)
      .build()
    assertThrows(AssertionError::class.java) {
      EventLogSubject.assertThat(eventLog)
        .isEssentialPriority()
    }
  }

  @Test
  fun testEventLogSubject_matchesPriorityOptional() {
    val eventLog = EventLog.newBuilder()
      .setPriority(EventLog.Priority.OPTIONAL)
      .build()

    EventLogSubject.assertThat(eventLog)
      .isOptionalPriority()
  }

  @Test
  fun testEventLogSubject_failsOnUnmatchingOptionalPriority() {
    val eventLog = EventLog.newBuilder()
      .setPriority(EventLog.Priority.ESSENTIAL)
      .build()
    assertThrows(AssertionError::class.java) {
      EventLogSubject.assertThat(eventLog)
        .isOptionalPriority()
    }
  }

  @Test
  fun testEventLogSubject_eventWithNoProfileId_returnsNoProfileId() {
    val eventLog = EventLog.newBuilder()
      .build()

    EventLogSubject.assertThat(eventLog)
      .hasNoProfileId()
  }

  @Test
  fun testEventLogSubject_eventWithProfileId_failsNoProfileExpected() {
    val profileId = ProfileId.newBuilder()
      .setInternalId(1)
      .build()
    val eventLog = EventLog.newBuilder()
      .setProfileId(profileId)
      .build()
    assertThrows(AssertionError::class.java) {
      EventLogSubject.assertThat(eventLog)
        .hasNoProfileId()
    }
  }

  @Test
  fun testEventLogSubject_matchesProfileIdPresent() {
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

  @Test
  fun testEventLogSubject_failsOnDifferentProfileId() {
    val profileId = ProfileId.newBuilder()
      .setInternalId(1)
      .build()
    val eventLog = EventLog.newBuilder()
      .setProfileId(profileId)
      .build()
    val differentProfileId = ProfileId.newBuilder()
      .setInternalId(2)
      .build()
    assertThrows(AssertionError::class.java) {
      EventLogSubject.assertThat(eventLog)
        .hasProfileIdThat()
        .isEqualTo(differentProfileId)
    }
  }

  @Test
  fun testEventLogSubject_matchesAppLanguageSelection() {
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

  @Test
  fun testEventLogSubject_failsOnDifferentAppLanguageSelectionPresent() {
    val appLanguageSelection = AppLanguageSelection.newBuilder()
      .setSelectedLanguage(OppiaLanguage.ENGLISH)
      .build()
    val eventLog = EventLog.newBuilder()
      .setAppLanguageSelection(appLanguageSelection)
      .build()
    val differentAppLanguageSelection = AppLanguageSelection.newBuilder()
      .setSelectedLanguage(OppiaLanguage.ARABIC)
      .build()
    assertThrows(AssertionError::class.java) {
      EventLogSubject.assertThat(eventLog)
        .hasAppLanguageSelectionThat()
        .isEqualTo(differentAppLanguageSelection)
    }
  }

  @Test
  fun testEventLogSubject_matchesWrittenTranslationLanguageSelection() {
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

  @Test
  fun testEventLogSubject_failsOnDifferentWrittenTranslationLanguageSelection() {
    val writtenLanguageSelection = WrittenTranslationLanguageSelection.newBuilder()
      .setSelectedLanguage(OppiaLanguage.ENGLISH)
      .build()
    val eventLog = EventLog.newBuilder()
      .setWrittenTranslationLanguageSelection(writtenLanguageSelection)
      .build()
    val differentLanguageSelection = WrittenTranslationLanguageSelection.newBuilder()
      .setSelectedLanguage(OppiaLanguage.ARABIC)
      .build()
    assertThrows(AssertionError::class.java) {
      EventLogSubject.assertThat(eventLog)
        .hasWrittenTranslationLanguageSelectionThat()
        .isEqualTo(differentLanguageSelection)
    }
  }

  @Test
  fun testEventLogSubject_matchesAudioTranslationLanguageSelection() {
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

  @Test
  fun testEventLogSubject_failsOnDifferentAudioTranslationLanguageSelection() {
    val audioTranslationLanguageSelection = AudioTranslationLanguageSelection.newBuilder()
      .setSelectedLanguage(OppiaLanguage.ENGLISH)
      .build()
    val eventLog = EventLog.newBuilder()
      .setAudioTranslationLanguageSelection(audioTranslationLanguageSelection)
      .build()
    val differentSelection = AudioTranslationLanguageSelection.newBuilder()
      .setSelectedLanguage(OppiaLanguage.ARABIC)
      .build()
    assertThrows(AssertionError::class.java) {
      EventLogSubject.assertThat(eventLog)
        .hasAudioTranslationLanguageSelectionThat()
        .isEqualTo(differentSelection)
    }
  }

  @Test
  fun testEventLogSubject_hasOpenExplorationActivityContext() {
    val eventLog = EventLog.newBuilder()
      .setContext(
        EventLog.Context.newBuilder()
          .setOpenExplorationActivity(ExplorationContext.newBuilder())
      )
      .build()

    EventLogSubject.assertThat(eventLog)
      .hasOpenExplorationActivityContext()
  }

  @Test
  fun testEventLogSubject_missingExplorationActivityContext_fails()  {
    val eventLog = EventLog.newBuilder()
      .build()
    assertThrows(AssertionError::class.java) {
      EventLogSubject.assertThat(eventLog)
        .hasOpenExplorationActivityContext()
    }
  }

  @Test
  fun testEventLogSubject_hasOpenInfoTabContext() {
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
  fun testEventLogSubject_hasOpenLessonsTabContext() {
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
  fun testEventLogSubject_hasOpenPracticeTabContextPresent() {
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
  fun testEventLogSubject_hasOpenRevisionTabContext() {
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
