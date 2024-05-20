import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IngestionApiTestModule } from '../../../test.module';
import { ApplicationReferenceTableUpdateComponent } from 'app/entities/application-reference-table/application-reference-table-update.component';
import { ApplicationReferenceTableService } from 'app/entities/application-reference-table/application-reference-table.service';
import { ApplicationReferenceTable } from 'app/shared/model/application-reference-table.model';

describe('Component Tests', () => {
  describe('ApplicationReferenceTable Management Update Component', () => {
    let comp: ApplicationReferenceTableUpdateComponent;
    let fixture: ComponentFixture<ApplicationReferenceTableUpdateComponent>;
    let service: ApplicationReferenceTableService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [ApplicationReferenceTableUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ApplicationReferenceTableUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApplicationReferenceTableUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApplicationReferenceTableService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ApplicationReferenceTable(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ApplicationReferenceTable();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
