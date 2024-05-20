import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IngestionApiSharedModule } from 'app/shared/shared.module';
import { ValidationNotesComponent } from './validation-notes.component';
import { ValidationNotesDetailComponent } from './validation-notes-detail.component';
import { ValidationNotesUpdateComponent } from './validation-notes-update.component';
import { ValidationNotesDeletePopupComponent, ValidationNotesDeleteDialogComponent } from './validation-notes-delete-dialog.component';
import { validationNotesRoute, validationNotesPopupRoute } from './validation-notes.route';

const ENTITY_STATES = [...validationNotesRoute, ...validationNotesPopupRoute];

@NgModule({
  imports: [IngestionApiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ValidationNotesComponent,
    ValidationNotesDetailComponent,
    ValidationNotesUpdateComponent,
    ValidationNotesDeleteDialogComponent,
    ValidationNotesDeletePopupComponent,
  ],
  entryComponents: [ValidationNotesDeleteDialogComponent],
})
export class IngestionApiValidationNotesModule {}
